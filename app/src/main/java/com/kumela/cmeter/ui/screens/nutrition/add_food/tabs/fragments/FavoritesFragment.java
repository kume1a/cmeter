package com.kumela.cmeter.ui.screens.nutrition.add_food.tabs.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kumela.cmeter.R;

/**
 * Created by Toko on 30,June,2020
 **/

public class FavoritesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_food_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        RecyclerView recyclerView = view.findViewById(R.id.rv_tab_add_food);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        SearchItemAdapter adapter = new SearchItemAdapter(this, );
//        ArrayList<SearchItem> list = new ArrayList<>();
//        list.add(new SearchItem("name", "slice", 1));
//        list.add(new SearchItem("name", "slice", 1));
//        list.add(new SearchItem("name", "slice", 1));
//        list.add(new SearchItem("name", "slice", 1));
//        list.add(new SearchItem("name", "slice", 1));
//        list.add(new SearchItem("name", "slice", 1));
//        adapter.submitList(list);
//        recyclerView.setAdapter(adapter);
    }
}
