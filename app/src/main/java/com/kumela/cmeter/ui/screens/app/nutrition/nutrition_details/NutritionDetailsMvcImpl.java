package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.model.api.food.Nutrients;
import com.kumela.cmeter.model.api.nutrients.TotalNutrient;
import com.kumela.cmeter.model.local.FoodNutrients;
import com.kumela.cmeter.model.local.list.NutritionDetailListModel;
import com.kumela.cmeter.ui.adapters.nutrition_details.NutritionDetailsAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;
import com.kumela.cmeter.ui.common.util.NutritionPieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailsMvcImpl extends BaseObservableViewMvc<NutritionDetailsMvc.Listener>
        implements NutritionDetailsMvc {

    private final ViewMvcFactory mViewMvcFactory;

    private ListPopupWindow mListPopupWindow;

    private AppCompatEditText mEtServingAmount;
    private MaterialTextView mTvServingUnit;
    private MaterialTextView mTvCarbohydrates, mTvFats, mTvProteins;
    private RecyclerView mRvNutritionDetails;
    private NutritionPieChart mPieChart;

    public NutritionDetailsMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.nutrition_details_fragment, parent, false));

        this.mViewMvcFactory = viewMvcFactory;

        mEtServingAmount = findViewById(R.id.et_nutrition_details_serving_amount);
        mTvServingUnit = findViewById(R.id.tv_nutrition_details_serving_unit);
        mRvNutritionDetails = findViewById(R.id.rv_nutrient_details);

        mTvCarbohydrates = findViewById(R.id.tv_nutrition_details_head_amount_carbs);
        mTvFats = findViewById(R.id.tv_nutrition_details_head_amount_fats);
        mTvProteins = findViewById(R.id.tv_nutrition_details_head_amount_protein);

        mPieChart = findViewById(R.id.pie_nutrition_details);
        mEtServingAmount.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    public void bindServingUnitInfo(List<Measure> measures) {
        final float initialQuantity;
        if (measures.size() != 0) {
            // populate list of serving units
            String[] servingUnits = new String[measures.size()];
            for (int i = 0; i < measures.size(); i++) {
                servingUnits[i] = measures.get(i).label;
            }

            // set first serving unit as default
            mTvServingUnit.setText(servingUnits[0]);

            // setup window popup for serving unit selection
            mListPopupWindow = new ListPopupWindow(getContext());
            mListPopupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
            mListPopupWindow.setAdapter(new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    servingUnits
            ));
            mListPopupWindow.setAnchorView(mTvServingUnit);
            mListPopupWindow.setModal(true);
            mListPopupWindow.setContentWidth(ListPopupWindow.WRAP_CONTENT);
            mListPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
                Measure measure = measures.get(position);

                mTvServingUnit.setText(measure.label);

                @SuppressWarnings("ConstantConditions")
                String value = mEtServingAmount.getText().toString();
                float quantity;
                if (!value.isEmpty()) {
                    quantity = Float.parseFloat(value);
                } else quantity = 0f;

                for (Listener listener : getListeners()) {
                    listener.onMeasureChanged(measure, quantity);
                }

                // set serving quantity for corresponding serving unit
                // float servingQuantity = Utils.getQuantity(measure.uri);
                // mEtServingAmount.setText(String.valueOf(servingQuantity));

                mListPopupWindow.dismiss();
            });
            mTvServingUnit.setOnClickListener(v -> mListPopupWindow.show());

            initialQuantity = Utils.getQuantity(servingUnits[0]);
        } else {
            mTvServingUnit.setText(getResources().getString(R.string.serving));
            initialQuantity = 1f;
        }

        mEtServingAmount.setText(String.valueOf((int) initialQuantity));
        mEtServingAmount.addTextChangedListener(new TextWatcher() {
            private Float mLastValue = initialQuantity;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try {
                    if (!s.toString().isEmpty()) {
                        mLastValue = Float.parseFloat(s.toString());
                    } else mLastValue = 0f;
                } catch (NumberFormatException e) {
                    mLastValue = 0f;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                float newValue;
                try {
                    if (!s.toString().isEmpty()) {
                        newValue = Float.parseFloat(s.toString());
                    } else newValue = 0f;
                } catch (NumberFormatException e) {
                    newValue = 0f;
                }

                if ((newValue - mLastValue) != 0) {
                    for (Listener listener : getListeners()) {
                        listener.onServingQuantityChanged(newValue);
                    }
                }
            }
        });
    }

    @Override
    public void bindNutritionInfo(FoodNutrients foodNutrients) {
        Nutrients nutrients = foodNutrients.getNutrients(false);

        bindBaseNutritionInfo(nutrients);
        bindChartInfo(nutrients);
        bindNutritionDetailsInfo(
                nutrients, foodNutrients.cholesterol,
                foodNutrients.getSubCarbohydrates(false), foodNutrients.getSubFats(false),
                foodNutrients.getMinerals(false), foodNutrients.getVitamins(false)
        );
    }

    private void bindNutritionDetailsInfo(Nutrients nutrients,
                                          TotalNutrient cholesterol,
                                          List<TotalNutrient> subCarbohydrates,
                                          List<TotalNutrient> subFats,
                                          List<TotalNutrient> minerals,
                                          List<TotalNutrient> vitamins) {
        NutritionDetailsAdapter nutritionDetailsAdapter = new NutritionDetailsAdapter(mViewMvcFactory);

        List<NutritionDetailListModel> nutritionDetails = new ArrayList<>();
        String g = getResources().getString(R.string.unit_gram);

        nutritionDetails.add(new NutritionDetailListModel(getResources().getString(R.string.calories), nutrients.calories, getResources().getString(R.string.cal), true));

        nutritionDetails.add(new NutritionDetailListModel(getResources().getString(R.string.carbohydrates), nutrients.carbohydrates, g, true));
        addToListModels(subCarbohydrates, nutritionDetails);

        nutritionDetails.add(new NutritionDetailListModel(getResources().getString(R.string.fats), nutrients.fats, g, true));
        addToListModels(subFats, nutritionDetails);

        nutritionDetails.add(new NutritionDetailListModel(getResources().getString(R.string.proteins), nutrients.proteins, g, true));
        if (cholesterol != null) {
            nutritionDetails.add(new NutritionDetailListModel(cholesterol.label, cholesterol.quantity, cholesterol.unit, true));
        }

        if (minerals.size() != 0) {
            nutritionDetails.add(new NutritionDetailListModel(getResources().getString(R.string.minerals), null, null, true));
            addToListModels(minerals, nutritionDetails);
        }
        if (vitamins.size() != 0) {
            nutritionDetails.add(new NutritionDetailListModel(getResources().getString(R.string.vitamins), null, null, true));
            addToListModels(vitamins, nutritionDetails);
        }
        nutritionDetailsAdapter.submitList(nutritionDetails);
        mRvNutritionDetails.setAdapter(nutritionDetailsAdapter);
    }

    private void addToListModels(List<TotalNutrient> totalNutrients,
                                 List<NutritionDetailListModel> nutritionDetails) {
        for (TotalNutrient totalNutrient : totalNutrients) {
            nutritionDetails.add(new NutritionDetailListModel(
                    totalNutrient.label,
                    totalNutrient.quantity,
                    totalNutrient.unit,
                    false
            ));
        }
    }

    private void bindBaseNutritionInfo(Nutrients nutrients) {
        String carbohydrates = (int) nutrients.carbohydrates + "";
        String fats = (int) nutrients.fats + "";
        String proteins = (int) nutrients.proteins + "";

        animateBaseNutritionTextView(mTvCarbohydrates, carbohydrates);
        animateBaseNutritionTextView(mTvFats, fats);
        animateBaseNutritionTextView(mTvProteins, proteins);
    }

    private void animateBaseNutritionTextView(TextView textView, String toValue) {
        String textViewValue = textView.getText().toString();
        int fromValue = 0;
        if (!textViewValue.isEmpty()) {
            fromValue = Integer.parseInt(textViewValue.substring(0, textViewValue.length() - 1));
        }

        ValueAnimator valueAnimator = ValueAnimator.ofInt(fromValue, Integer.parseInt(toValue));
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(200);
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            textView.setText(getResources().getString(R.string.value_g, String.valueOf(value)));
        });
        valueAnimator.start();
    }

    private void bindChartInfo(Nutrients nutrients) {
        Map<Float, Integer> data = new HashMap<>();
        data.put(nutrients.carbohydrates, getResources().getColor(R.color.colorCarbs));
        data.put(nutrients.fats, getResources().getColor(R.color.colorFats));
        data.put(nutrients.proteins, getResources().getColor(R.color.colorProtein));

        mPieChart.setHoleRadius((int) getResources().getDimension(R.dimen.pie_nutrition_details), .28f);
        mPieChart.setCenterText(String.format(getResources().getString(R.string.value_unit), (int) nutrients.calories + "", getResources().getString(R.string.cal)), 18);
        mPieChart.setPieData(data);
        mPieChart.animateY();
    }

}
