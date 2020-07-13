package com.kumela.cmeter.ui.common.util;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kumela.cmeter.common.BaseObservable;
import com.kumela.cmeter.model.list.NutritionDetailItem;
import com.kumela.cmeter.model.api.nutrition.FullNutrient;
import com.kumela.cmeter.model.api.nutrition.NutritionInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

    @Inject
    public NutritionInfoParser() {
    }

    public void parseNutritionInfoAndNotify(NutritionInfo nutritionInfo) {
        new Parser(nutritionInfo.fullNutrients, getListeners()).execute();
    }

    private static class Parser extends AsyncTask<Void, Void, List<NutritionDetailItem>> {

        private Map<String, Float> mIdToName;
        private Set<Listener> mListeners;
        private List<FullNutrient> mFullNutrients;

        private static final String ID = "id";
        private static final String NAME = "name";
        private static final String UNIT = "unit";
        private static final String HEADER = "header";
        private static final String NO_VALUE = "no_value";

        private Parser(@NonNull List<FullNutrient> fullNutrients, @NonNull Set<Listener> listeners) {
            this.mListeners = listeners;
            this.mFullNutrients = fullNutrients;
        }

        private void mapIdToNames(@NonNull List<FullNutrient> fullNutrients) {
            mIdToName = new HashMap<>();

            for (FullNutrient fullNutrient : fullNutrients) {
                mIdToName.put(
                        Integer.toString(fullNutrient.id),
                        fullNutrient.value
                );
            }
        }

        @Nullable
        @Override
        protected List<NutritionDetailItem> doInBackground(Void... voids) {
            mapIdToNames(mFullNutrients);

            String json = loadJson();
            List<NutritionDetailItem> nutritionDetails;
            if (json != null) {
                Log.d(TAG, "doInBackground: parsing nutritionDetails");
                nutritionDetails = new ArrayList<>();

                try {
                    JSONArray jsonArray = new JSONArray(json);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = jsonArray.getJSONObject(i);

                        if (o.has(NO_VALUE)) {
                            nutritionDetails.add(new NutritionDetailItem(
                                    o.getString(NAME), 0f, "",
                                    true, false
                            ));
                        } else {
                            NutritionDetailItem nutritionDetailItem =  getNutritionDetailItem(o);
                            if (nutritionDetailItem != null) {
                                nutritionDetails.add(getNutritionDetailItem(o));
                            } // if getNutritionDetailItem returns null then there is no value and skip
                        }
                    }
                    
                    return nutritionDetails;
                } catch (JSONException e) {
                    Log.e(TAG, "doInBackground: ", e);
                }
            }
            Log.d(TAG, "doInBackground: json is null returning");
            return null;
        }

        @Override
        protected void onPostExecute(List<NutritionDetailItem> nutritionDetailItems) {
            super.onPostExecute(nutritionDetailItems);
            for (Listener listener : mListeners) {
                if (nutritionDetailItems != null) {
                    Log.d(TAG, "onPostExecute: parsing succeeded, notifying listener");
                    listener.onNutritionInfoParsed(nutritionDetailItems);
                } else {
                    Log.d(TAG, "onPostExecute: parsing failed, notifying listener");
                    listener.onNutritionInfoParseFailed();
                }
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

        @Nullable
        @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
        private String loadJson() {
            String json;
            InputStream is = null;
            try {
                is = getClass().getClassLoader().getResourceAsStream("res/raw/mapping.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                json = new String(buffer, StandardCharsets.UTF_8);
            } catch (IOException e) {
                Log.e(TAG, "loadJson: ", e);
                return null;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return json;
        }
    }
}
