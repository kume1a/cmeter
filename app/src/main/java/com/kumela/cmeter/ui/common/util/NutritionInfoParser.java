package com.kumela.cmeter.ui.common.util;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.model.api.nutrition.FullNutrient;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;
import com.kumela.cmeter.model.firebase.AddedFood;
import com.kumela.cmeter.model.local.NutritionDetailItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Toko on 03,July,2020
 **/

public class NutritionInfoParser extends BaseObservable<NutritionInfoParser.Listener> {

    private static final String TAG = "NutritionInfoParser";

    public interface Listener {
        void onNutritionInfoParsed(List<NutritionDetailItem> nutritionDetails);

        void onNutritionInfoParseFailed();
    }

    private final Handler mUiHandler;

    @Inject
    public NutritionInfoParser(Handler uiHandler) {
        this.mUiHandler = uiHandler;
    }

    public void parseNutritionInfoAndNotify(ArrayList<AddedFood> addedFoods) {
        ArrayList<NutritionInfo> nutritionInfos = new ArrayList<>();
        for (AddedFood addedFood: addedFoods) {
            nutritionInfos.add(addedFoodToNutritionInfo(addedFood));
        }

        new Thread(new Parser(
                getListeners(),
                mUiHandler,
                nutritionInfos
        )).start();
    }

    public void parseNutritionInfoAndNotify(NutritionInfo nutritionInfo) {
        new Thread(new Parser(
                getListeners(),
                mUiHandler,
                new ArrayList<>(Collections.singletonList(nutritionInfo))
        )).start();
    }

    private NutritionInfo addedFoodToNutritionInfo(AddedFood addedFood) {
        return new NutritionInfo(
                addedFood.foodName,
                addedFood.currentServingQuantity,
                addedFood.servingUnit,
                addedFood.servingWeightInGrams,
                addedFood.totalCalories,
                addedFood.totalCarbohydrates,
                addedFood.totalFats,
                addedFood.totalProteins,
                addedFood.fullNutrients
        );
    }

    private static class Parser implements Runnable {

        private final Set<Listener> mListeners;
        private final Handler mUiHandler;
        private final List<NutritionInfo> mNutritionInfos;

        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String UNIT = "unit";
        private static final String HEADER = "header";
        private static final String NO_VALUE = "no_value";

        private Parser(@NonNull Set<Listener> listeners,
                       @NonNull Handler uiHandler,
                       @NonNull List<NutritionInfo> nutritionInfos) {
            Log.d(TAG, "Parser: called, nutitionInfos = " + nutritionInfos);

            this.mListeners = listeners;
            this.mUiHandler = uiHandler;
            this.mNutritionInfos = nutritionInfos;
        }

        private Map<String, Float> mIdToName;
        private List<NutritionDetailItem> mNutritionDetailItems;
        private String json;

        @Override
        public void run() {
            loadJson();
            if (json == null) notifyOnFailure();

            for (NutritionInfo nutritionInfo : mNutritionInfos) {
                mapIdToNames(nutritionInfo.fullNutrients, nutritionInfo.zeroedOut);
                parseToNutritionDetailItemsAndNotify();
            }

            notifyOnSuccess();
        }

        @SuppressWarnings("ConstantConditions")
        private void onNutritionDetailsParsed(List<NutritionDetailItem> nutritionDetailItems) {
            if (mNutritionDetailItems == null) {
                mNutritionDetailItems = nutritionDetailItems;
            } else {
                // map new names to indices
                Map<String, Integer> mapNameToIndex = new HashMap<>();
                for (int i = 0; i < nutritionDetailItems.size(); i++) {
                    mapNameToIndex.put(nutritionDetailItems.get(i).getName(), i);
                }

                // increment new values to result
                for (NutritionDetailItem nutritionDetailItem : mNutritionDetailItems) {
                    String name = nutritionDetailItem.getName();
                    if (mapNameToIndex.containsKey(name)) {
                        int index = mapNameToIndex.get(nutritionDetailItem.getName());
                        NutritionDetailItem currentItem = nutritionDetailItems.get(index);

                        if (currentItem != null) {
                            nutritionDetailItem.incrementValue(currentItem.getValue());
                        }
                    }
                }
            }
        }

        private void notifyOnFailure() {
            mUiHandler.post(() -> {
                for (Listener listener : mListeners) {
                    listener.onNutritionInfoParseFailed();
                }
            });
        }

        private void notifyOnSuccess() {
            mUiHandler.post(() -> {
                if (mNutritionDetailItems != null) {
                    for (Listener listener : mListeners) {
                        listener.onNutritionInfoParsed(mNutritionDetailItems);
                    }
                } else {
                    notifyOnFailure();
                    Log.d(TAG, "notifyOnSuccess: nutritionDetailItems = " + mNutritionDetailItems);
                }
            });
        }

        private void mapIdToNames(@NonNull List<FullNutrient> fullNutrients, boolean zeroedOut) {
            mIdToName = new HashMap<>();

            for (FullNutrient fullNutrient : fullNutrients) {
                mIdToName.put(
                        Integer.toString(fullNutrient.id),
                        zeroedOut ? 0 : fullNutrient.value
                );
            }
        }

        private void parseToNutritionDetailItemsAndNotify() {
            try {
                List<NutritionDetailItem> nutritionDetails = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject o = jsonArray.getJSONObject(i);

                    if (o.has(NO_VALUE)) {
                        nutritionDetails.add(new NutritionDetailItem(o.getString(NAME), 0f, "", true, false));
                    } else {
                        NutritionDetailItem nutritionDetailItem = getNutritionDetailItem(o);
                        if (nutritionDetailItem != null) {
                            nutritionDetails.add(getNutritionDetailItem(o));
                        } // if getNutritionDetailItem returns null then there is no value and skip
                    }
                }

                onNutritionDetailsParsed(nutritionDetails);
            } catch (JSONException e) {
                Log.e(TAG, "doInBackground: ", e);
            }
        }

        @Nullable
        private NutritionDetailItem getNutritionDetailItem(JSONObject o) {
            try {
                String id = o.getString(ID);
                Float value = mIdToName.get(id);

                if (value == null) return null;

                String name = o.getString(NAME);
                String unit = o.getString(UNIT);
                boolean isHeader = o.has(HEADER);

                return new NutritionDetailItem(name, value, unit, isHeader, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
        private void loadJson() {
            InputStream is = null;
            try {
                is = getClass().getClassLoader().getResourceAsStream("res/raw/mapping.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException e) {
                Log.e(TAG, "loadJson: ", e);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
