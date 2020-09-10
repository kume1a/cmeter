package com.kumela.cmeter.ui.screens.app.nutrition.nutrition_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.kumela.cmeter.R;
import com.kumela.cmeter.common.Utils;
import com.kumela.cmeter.model.api.food.Measure;
import com.kumela.cmeter.model.local.FoodNutrients;
import com.kumela.cmeter.ui.common.base.BaseFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Toko on 30,June,2020
 **/

public class NutritionDetailsFragment extends BaseFragment
        implements NutritionDetailsMvc.Listener, NutritionDetailsViewModel.Listener {

    private NutritionDetailsMvc mViewMvc;
    private NutritionDetailsViewModel mViewModel;
    private NutritionDetailsNavController mNavController;

    // args
    private String foodId;
    private String foodName;
    private String meal;
    private List<Measure> measures;

    private boolean favorite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mViewMvc = getViewMvcFactory().newInstance(NutritionDetailsMvc.class, container);
        return mViewMvc.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get args from fragment
        NutritionDetailsFragmentArgs args = NutritionDetailsFragmentArgs.fromBundle(requireArguments());
        foodId = args.getFoodId();
        foodName = args.getFoodName();
        meal = args.getMeal();
        measures = Arrays.asList(args.getMeasures());

        // initialize view model and observe from live data
        mViewModel = new ViewModelProvider(this, getViewModelFactory()).get(NutritionDetailsViewModel.class);
        // TODO: 8/5/2020 check if food was marked as favorite

        if (savedInstanceState == null) {
            String measureUri = measures.get(0).uri;
            float quantity = Utils.getQuantity(measureUri);

            mViewModel.fetchNutritionInfoAndNotify(foodId, quantity, measureUri);
        }

        mNavController = getNavControllerFactory().newInstance(NutritionDetailsNavController.class, mViewMvc.getRootView());

        mViewMvc.bindServingUnitInfo(measures);
    }

    @Override
    public void onStart() {
        super.onStart();

        mViewMvc.registerListener(this);
        mViewModel.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mViewModel.unregisterListener(this);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_nutrition_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_nutrition_details_done:
                mViewModel.writeProduct(measures, meal, favorite);
                return true;
            case R.id.menu_nutrition_details_favorite:
                if (favorite) {
                    item.setIcon(R.drawable.ic_favorite);
                } else item.setIcon(R.drawable.ic_favorite_filled);
                favorite = !favorite;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMeasureChanged(@NonNull Measure measure, float quantity) {
        mViewModel.fetchNutritionInfoAndNotify(foodId, quantity, measure.uri);
    }

    @Override
    public void onServingQuantityChanged(float servingQuantity) {
        mViewModel.onServingQuantityChanged(servingQuantity);
    }

    @Override
    public void onProvideFoodNutrients(FoodNutrients foodNutrients) {
        mViewMvc.bindNutritionInfo(foodNutrients);
    }

    @Override
    public void onProvideFoodNutrientsFailed() {
        Toast.makeText(getContext(), "NUTRIENTS = NULL", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddFoodSucceeded() {
        mNavController.actionBack();
    }

    @Override
    public void onAddFoodFailed() {
        Toast.makeText(getContext(), "ADD FOOD FAILED", Toast.LENGTH_SHORT).show();
    }

    /*@Override
    public void onNutritionInfoUpdated(NutritionInfo nutritionInfo) {
        if (nutritionInfo.zeroedOut) {
            mViewMvc.updateNutritionInfo(nutritionInfo.getZeroedOutInstance());
        } else mViewMvc.updateNutritionInfo(nutritionInfo);
    }*/
}
