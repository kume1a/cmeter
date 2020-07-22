package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.animation.ValueAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.api.Photo;
import com.kumela.cmeter.model.api.nutrition.AltMeasure;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.local.BaseNutrition;
import com.kumela.cmeter.model.local.NutritionDetailItem;
import com.kumela.cmeter.ui.adapters.nutrition_details.NutritionDetailsAdapter;
import com.kumela.cmeter.ui.common.mvc.observanble.BaseObservableViewMvc;
import com.kumela.cmeter.ui.common.util.NutritionPieChart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailsMvcImpl extends BaseObservableViewMvc<NutritionDetailsMvc.Listener>
        implements NutritionDetailsMvc {

    private static final String TAG = "FoodDetailsMvcImpl";

    private final ViewMvcFactory mViewMvcFactory;

    private MaterialTextView mTvCarbohydrates, mTvFats, mTvProteins;

    private AppCompatImageButton mBtnArrowRevealMore;
    private LinearLayout mLLNutritionDetails;
    private FloatingActionButton mFabAdd;

    private AppCompatEditText mEtServingAmount;
    private MaterialTextView mTvServingUnit;

    private ListPopupWindow mListWindowPopup;
    private NutritionPieChart mPieChart;

    private NutritionDetailsAdapter mNutritionDetailsAdapter, mCollapsibleNutritionDetailsAdapter;

    public NutritionDetailsMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.nutrition_details_activity, parent, false));

        this.mViewMvcFactory = viewMvcFactory;

        mLLNutritionDetails = findViewById(R.id.ll_nutrition_details);
        mBtnArrowRevealMore = findViewById(R.id.btn_food_details_arrow);
        mFabAdd = findViewById(R.id.fab_food_details);

        mEtServingAmount = findViewById(R.id.et_nutrition_details_serving_amount);
        mTvServingUnit = findViewById(R.id.tv_nutrition_details_serving_unit);

        mTvCarbohydrates = findViewById(R.id.tv_food_details_head_amount_carbs);
        mTvFats = findViewById(R.id.tv_food_details_head_amount_fats);
        mTvProteins = findViewById(R.id.tv_food_details_head_amount_protein);

        mPieChart = findViewById(R.id.pie_food_details);
    }

    @Override
    public void showFab() {
        mFabAdd.show();
        mFabAdd.setOnClickListener(v -> {
            for (Listener listener : getListeners()) listener.onFabClicked();
        });
    }

    @Override
    public void bindNutritionDetails(List<NutritionDetailItem> nutritionDetails) {
        RecyclerView recyclerView = findViewById(R.id.rv_nutrient_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView collapsibleRecyclerView = findViewById(R.id.rv_nutrient_details_collapsible);
        collapsibleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        int index = -1;
        for (NutritionDetailItem item : nutritionDetails) {
            if ("Protein".equals(item.getName())) {
                index = nutritionDetails.indexOf(item);
            } else if ("Water".equals(item.getName())) {
                index = nutritionDetails.indexOf(item);
                break;
            }
        }
        if (index == -1)
            throw new RuntimeException("mapping.json should include Protein or Water field inside");

        List<NutritionDetailItem> nutritionDetailItems = nutritionDetails.subList(0, index);
        List<NutritionDetailItem> collapsibleNutritionDetailItems = nutritionDetails.subList(index, nutritionDetails.size());

        mNutritionDetailsAdapter = new NutritionDetailsAdapter(mViewMvcFactory);
        mCollapsibleNutritionDetailsAdapter = new NutritionDetailsAdapter(mViewMvcFactory);

        mNutritionDetailsAdapter.submitList(nutritionDetailItems);
        mCollapsibleNutritionDetailsAdapter.submitList(collapsibleNutritionDetailItems);

        recyclerView.setAdapter(mNutritionDetailsAdapter);
        collapsibleRecyclerView.setAdapter(mCollapsibleNutritionDetailsAdapter);

        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.recycler_top_to_bottom_fade_in));
        collapsibleRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.recycler_top_to_bottom_fade_in));

        mBtnArrowRevealMore.setOnClickListener(v -> {
            if (collapsibleRecyclerView.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(mLLNutritionDetails, new AutoTransition());
                collapsibleRecyclerView.setVisibility(View.VISIBLE);
                mBtnArrowRevealMore.animate().rotation(180).start();
            } else {
                TransitionManager.beginDelayedTransition(mLLNutritionDetails, new AutoTransition());
                collapsibleRecyclerView.setVisibility(View.GONE);
                mBtnArrowRevealMore.animate().rotation(0).start();
            }
        });
    }

    @Override
    public void updateNutritionDetails(List<NutritionDetailItem> nutritionDetails) {
        int index = -1;
        for (NutritionDetailItem item : nutritionDetails) {
            if ("Water".equals(item.getName()) || "Protein".equals(item.getName())) {
                index = nutritionDetails.indexOf(item);
                break;
            }
        }
        if (index == -1)
            throw new RuntimeException("mapping.json should include Protein or Water field inside");

        if (mNutritionDetailsAdapter == null || mCollapsibleNutritionDetailsAdapter == null) {
            bindNutritionDetails(nutritionDetails);
        } else {
            List<NutritionDetailItem> nutritionDetailItems = nutritionDetails.subList(0, index);
            List<NutritionDetailItem> collapsibleNutritionDetailItems = nutritionDetails.subList(index, nutritionDetails.size());

            mNutritionDetailsAdapter.submitList(nutritionDetailItems);
            mCollapsibleNutritionDetailsAdapter.submitList(collapsibleNutritionDetailItems);
        }
    }

    @Override
    public void bindNutritionInfo(NutritionInfo nutritionInfo) {
        bindImage(nutritionInfo.photo);

        BaseNutrition baseNutrition = new BaseNutrition(
                nutritionInfo.totalCalories,
                nutritionInfo.totalCarbohydrates,
                nutritionInfo.totalFats,
                nutritionInfo.totalProteins
        );

        bindServingUnitInfo(new HashSet<>(nutritionInfo.altMeasures), nutritionInfo.servingUnit, nutritionInfo.currentServingQuantity);
        bindBaseNutritionInfo(baseNutrition);
        bindChartInfo(baseNutrition);
    }

    @Override
    public void updateNutritionInfo(NutritionInfo nutritionInfo) {
        BaseNutrition baseNutrition = new BaseNutrition(
                nutritionInfo.totalCalories,
                nutritionInfo.totalCarbohydrates,
                nutritionInfo.totalFats,
                nutritionInfo.totalProteins
        );

        updateBaseNutritionInfo(baseNutrition);
        bindChartInfo(baseNutrition);
    }

    private void bindServingUnitInfo(Set<AltMeasure> altMeasuresSet, String defaultUnit, float defaultQuantity) {
        // filter altMeasures
        final ArrayList<AltMeasure> altMeasures = new ArrayList<>(altMeasuresSet);
        float initialQuantity;

        if (altMeasures.size() > 0) {
            // populate list of serving units
            ArrayList<String> servingUnits = new ArrayList<>();
            for (AltMeasure altMeasure : altMeasures) {
                servingUnits.add(altMeasure.measure);
            }

            // set first serving unit as default
            mTvServingUnit.setText(servingUnits.get(0));

            // setup window popup for serving unit selection
            mListWindowPopup = new ListPopupWindow(getContext());
            mListWindowPopup.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
            mListWindowPopup.setAdapter(new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    servingUnits
            ));
            mListWindowPopup.setAnchorView(mTvServingUnit);
            mListWindowPopup.setModal(true);
            mListWindowPopup.setOnItemClickListener((parent, view, position, id) -> {
                AltMeasure altMeasure = altMeasures.get(position);

                for (Listener listener : getListeners()) {
                    listener.onAltMeasureChanged(altMeasure);
                }

                String measureUnit = (String) parent.getItemAtPosition(position);
                mTvServingUnit.setText(measureUnit);

                float servingQuantity = altMeasure.qty;
                mEtServingAmount.setText(String.valueOf(servingQuantity));
                for (Listener listener : getListeners())
                    listener.onServingQuantityChanged(servingQuantity);

                mListWindowPopup.dismiss();
            });

            // set onClickListener on serving unit textView to show window
            mTvServingUnit.setOnClickListener(v -> mListWindowPopup.show());

            // set initial value to first serving quantity
            initialQuantity = altMeasures.get(0).qty;
        } else { // alt measure's size is 0

            // set default serving unit and don't enable popup
            mTvServingUnit.setText(defaultUnit);

            // assign default initial quantity to local field
            initialQuantity = defaultQuantity;
            mTvServingUnit.setCompoundDrawables(null, null, null, null);
        }

        float finalInitialQuantity = initialQuantity;
        mEtServingAmount.setText(String.valueOf(finalInitialQuantity));

        // set textWatcher to serving quantity edit text
        mEtServingAmount.addTextChangedListener(new TextWatcher() {
            private Float mLastValue = finalInitialQuantity;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!s.toString().isEmpty()) {
                    mLastValue = Float.parseFloat(s.toString());
                } else mLastValue = 0f;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) return;

                float newValue = Float.parseFloat(s.toString());
                if ((newValue - mLastValue) != 0) {
                    for (Listener listener : getListeners()) {
                        listener.onServingQuantityChanged(newValue);
                    }
                }
            }
        });
    }

    private void bindImage(Photo photo) {
        if (photo.highRes != null) {
            Log.d(TAG, "bindImage: setting image");
            AppCompatImageView imageView = findViewById(R.id.iv_food_details_header);

            Picasso.get()
                    .load(photo.highRes)
                    .centerCrop()
                    .fit()
                    .into(imageView);
        } else {
            // No image found set expanded title gravity to center
            CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.ctl_nutrition_details);
            collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);
        }
    }

    private void bindBaseNutritionInfo(BaseNutrition baseNutrition) {
        String carbohydrates = (int) baseNutrition.getCarbohydrates() + "";
        String fats = (int) baseNutrition.getFats() + "";
        String proteins = (int) baseNutrition.getProteins() + "";

        mTvCarbohydrates.setText(String.format(getResources().getString(R.string.value_g), carbohydrates));
        mTvFats.setText(String.format(getResources().getString(R.string.value_g), fats));
        mTvProteins.setText(String.format(getResources().getString(R.string.value_g), proteins));
    }

    private void updateBaseNutritionInfo(BaseNutrition baseNutrition) {
        String carbohydrates = (int) baseNutrition.getCarbohydrates() + "";
        String fats = (int) baseNutrition.getFats() + "";
        String proteins = (int) baseNutrition.getProteins() + "";

        animateBaseNutritionTextView(mTvCarbohydrates, carbohydrates);
        animateBaseNutritionTextView(mTvFats, fats);
        animateBaseNutritionTextView(mTvProteins, proteins);
    }

    private void animateBaseNutritionTextView(TextView textView, String toValue) {
        String textViewValue = textView.getText().toString();
        int fromValue = Integer.parseInt(textViewValue.substring(0, textView.length() - 1));

        ValueAnimator valueAnimator = ValueAnimator.ofInt(fromValue, Integer.parseInt(toValue));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            textView.setText(getResources().getString(R.string.value_g, String.valueOf(value)));
        });
        valueAnimator.start();
    }

    private void bindChartInfo(BaseNutrition baseNutrition) {
        Map<Float, Integer> data = new HashMap<>();
        data.put(baseNutrition.getCarbohydrates(), getResources().getColor(R.color.colorCarbs));
        data.put(baseNutrition.getFats(), getResources().getColor(R.color.colorFats));
        data.put(baseNutrition.getProteins(), getResources().getColor(R.color.colorProtein));

        mPieChart.setHoleRadius((int) getResources().getDimension(R.dimen.pie_nutrition_details), .28f);
        mPieChart.setCenterText(String.format(getResources().getString(R.string.value_unit), (int) baseNutrition.getCalories() + "", getResources().getString(R.string.cal)), 18);
        mPieChart.setPieData(data);
        mPieChart.animateY();
    }

}
