package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textview.MaterialTextView;
import com.kumela.cmeter.R;
import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.list.BaseNutrition;
import com.kumela.cmeter.model.list.NutritionDetailItem;
import com.kumela.cmeter.model.api.Photo;
import com.kumela.cmeter.ui.adapters.nutrition_details.NutritionDetailsAdapter;
import com.kumela.cmeter.ui.common.util.NutritionPieChart;
import com.kumela.cmeter.ui.common.mvc.BaseViewMvc;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionDetailsMvcImpl extends BaseViewMvc implements NutritionDetailsMvc {

    private static final String TAG = "FoodDetailsMvcImpl";

    private final ViewMvcFactory mViewMvcFactory;

    public NutritionDetailsMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.nutrition_details_activity, parent, false));

        this.mViewMvcFactory = viewMvcFactory;
    }

    @Override
    public void bindRecyclerNutritionInfo(List<NutritionDetailItem> nutritionDetails) {
        RecyclerView recyclerView = setupRecyclerView(R.id.rv_nutrient_details);
        RecyclerView collapsibleRecyclerView = setupRecyclerView(R.id.rv_nutrient_details_collapsible);

        int index = getIndex(nutritionDetails);

        List<NutritionDetailItem> list = nutritionDetails.subList(0, index);
        List<NutritionDetailItem> collapsibleList = nutritionDetails.subList(index, nutritionDetails.size());

        NutritionDetailsAdapter adapter = new NutritionDetailsAdapter(mViewMvcFactory, list);
        NutritionDetailsAdapter collapsibleAdapter = new NutritionDetailsAdapter(mViewMvcFactory, collapsibleList);

        recyclerView.setAdapter(adapter);
        collapsibleRecyclerView.setAdapter(collapsibleAdapter);

        CardView cardView = findViewById(R.id.cv_nutrition_details);
        AppCompatImageButton arrowBtn = findViewById(R.id.btn_arrow_food_details);
        NestedScrollView scrollView = findViewById(R.id.sv_nutrition_details);

        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(
                (ViewTreeObserver.OnGlobalLayoutListener) () -> scrollView.post(
                        (Runnable) () -> scrollView.fullScroll(View.FOCUS_DOWN)
                )
        );

        recyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.recycler_top_to_bottom_fade_in));

        arrowBtn.setOnClickListener(v -> {
            if (collapsibleRecyclerView.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                collapsibleRecyclerView.setVisibility(View.VISIBLE);
                arrowBtn.animate().rotation(180).setDuration(600).start();
            } else {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                collapsibleRecyclerView.setVisibility(View.GONE);
                arrowBtn.animate().rotation(0).setDuration(200).start();
            }
        });
    }

    private int getIndex(List<NutritionDetailItem> nutritionDetails) {
        for (NutritionDetailItem item : nutritionDetails) {
            if ("Protein".equals(item.getName())) {
                return nutritionDetails.indexOf(item);
            }
        }
        throw new RuntimeException();
    }

    private RecyclerView setupRecyclerView(int recyclerId) {
        RecyclerView recyclerView = findViewById(recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return recyclerView;
    }

    @Override
    public void bindImage(Photo photo) {
        Log.d(TAG, "bindImage: called, photo = " + photo);
        AppBarLayout appBarLayout = findViewById(R.id.abl_food_details);

        if (photo.highRes != null) {
            Log.d(TAG, "bindImage: setting image");
            AppCompatImageView imageView = findViewById(R.id.iv_food_details_header);

            Picasso.get()
                    .load(photo.highRes)
                    .centerCrop()
                    .fit()
                    .into(imageView);
        } else {
            Log.d(TAG, "bindImage: no image found disabling toolbar");

            appBarLayout.setExpanded(false, true);
            appBarLayout.setActivated(false);
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();

            TypedValue tv = new TypedValue();
            if (getContext().getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                lp.height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
        }
    }

    @Override
    public void bindBaseNutritionInfo(BaseNutrition baseNutrition) {
        MaterialTextView tvCarbohydrates = findViewById(R.id.tv_food_details_head_amount_carbs);
        MaterialTextView tvFats = findViewById(R.id.tv_food_details_head_amount_fats);
        MaterialTextView tvProteins = findViewById(R.id.tv_food_details_head_amount_protein);

        tvCarbohydrates.setText(String.format(getResources().getString(R.string.value_g), baseNutrition.getCarbohydrates()));
        tvFats.setText(String.format(getResources().getString(R.string.value_g), baseNutrition.getFats()));
        tvProteins.setText(String.format(getResources().getString(R.string.value_g), baseNutrition.getProteins()));

        setupChart(baseNutrition);
    }

    private void setupChart(BaseNutrition baseNutrition) {
        Map<String, Integer> data = new HashMap<>();
        data.put(baseNutrition.getCarbohydrates(), getResources().getColor(R.color.colorCarbs));
        data.put(baseNutrition.getFats(), getResources().getColor(R.color.colorFats));
        data.put(baseNutrition.getProteins(), getResources().getColor(R.color.colorProtein));

        NutritionPieChart pieChart = findViewById(R.id.pie_food_details);

        pieChart.getViewTreeObserver().addOnGlobalLayoutListener(() -> pieChart.setHoleRadius(pieChart.getWidth()));

        pieChart.setCenterText(baseNutrition.getCalories());
        pieChart.setPieData(data);
        pieChart.animateY();
    }
}
