package com.kumela.cmeter.ui.adapters.added_food;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kumela.cmeter.common.di.factory.ViewMvcFactory;
import com.kumela.cmeter.model.local.list.ProductHistoryListModel;

/**
 * Created by Toko on 21,July,2020
 **/

public class ProductHistoryAdapter
        extends ListAdapter<ProductHistoryListModel, ProductHistoryAdapter.ProductHistoryViewHolder> {

    public static final int ADAPTER_TYPE_RECENT = 0;
    public static final int ADAPTER_TYPE_FAVORITES = 1;
    public static final int ADAPTER_TYPE_NONE = 2;


    public interface Listener {
        void onProductClicked(ProductHistoryListModel historyListModel);
    }

    private final Listener mListener;
    private final ViewMvcFactory mViewMvcFactory;
    private final int mAdapterType;

    public ProductHistoryAdapter(Listener listener,
                                 ViewMvcFactory viewMvcFactory,
                                 @IntRange(from = 0, to = 2) int adapterType) {
        super(DIFF);

        this.mListener = listener;
        this.mViewMvcFactory = viewMvcFactory;
        this.mAdapterType = adapterType;
    }

    private static final DiffUtil.ItemCallback<ProductHistoryListModel> DIFF = new DiffUtil.ItemCallback<ProductHistoryListModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProductHistoryListModel oldItem, @NonNull ProductHistoryListModel newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductHistoryListModel oldItem, @NonNull ProductHistoryListModel newItem) {
            return oldItem.getFoodId().equals(newItem.getFoodId()) &&
                    oldItem.getFoodName().equals(newItem.getFoodName()) &&
                    oldItem.getQuantity() == newItem.getQuantity() &&
                    oldItem.getMeasureUnit().equals(newItem.getMeasureUnit()) &&
                    oldItem.getCalories() == newItem.getCalories() &&
                    oldItem.isFavorite() && newItem.isFavorite() &&
                    oldItem.getDate().equals(newItem.getDate());
        }
    };

    @NonNull
    @Override
    public ProductHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemMvc viewMvc = mViewMvcFactory.newInstance(ProductItemMvc.class, parent);
        return new ProductHistoryViewHolder(viewMvc);
    }

    private String mLastDate;

    @Override
    public void onBindViewHolder(@NonNull ProductHistoryViewHolder holder, int position) {
        ProductHistoryListModel item = getItem(position);

        switch (mAdapterType) {
            case ADAPTER_TYPE_RECENT:
                boolean showDate = !item.getDate().equals(mLastDate);
                if (item.getFoodName().equalsIgnoreCase("Fish")) {
                    String TAG = getClass().getSimpleName();
                    Log.d(TAG, "onBindViewHolder: showDate = " + showDate);
                }
                holder.mProductItemMvc.bindHistoryProduct(item, showDate, mLastDate == null, false);
                mLastDate = item.getDate();
                break;
            case ADAPTER_TYPE_FAVORITES:
                holder.mProductItemMvc.bindHistoryProduct(item, false, false, false);
                break;
            case ADAPTER_TYPE_NONE:
                holder.mProductItemMvc.bindHistoryProduct(item, false, false, true);
                break;
        }
    }

    static class ProductHistoryViewHolder extends RecyclerView.ViewHolder {
        private final ProductItemMvc mProductItemMvc;

        private ProductHistoryViewHolder(ProductItemMvc productItemMvc) {
            super(productItemMvc.getRootView());
            this.mProductItemMvc = productItemMvc;
        }
    }
}
