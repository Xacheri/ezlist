package com.example.ez_list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.GroceryList;
import com.example.ez_list.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class GroceryItemFragment extends Fragment implements OnDeleteItemClick {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    GroceryItemViewModel groceryItemViewModel; // item behavior
    GroceryListViewModel groceryListViewModel; // container behavior
    GroceryItemRecyclerViewAdapter adpt;
    public GroceryItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GroceryItemFragment newInstance(int columnCount) {
        GroceryItemFragment fragment = new GroceryItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        groceryItemViewModel = new ViewModelProvider(getActivity()).get(GroceryItemViewModel.class);
        groceryListViewModel = new ViewModelProvider(getActivity()).get(GroceryListViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grocery_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adpt = new GroceryItemRecyclerViewAdapter(new ArrayList<>(), this);
            recyclerView.setAdapter(adpt);



            groceryListViewModel.getListInProgress().observe(getViewLifecycleOwner(), listModel -> {
                if(listModel != null)
                {
                    adpt.updateList(listModel.list);
                }
            });
        }


        return view;
    }


    @Override
    public void OnDeleteItemClick(Grocery item) {
        groceryListViewModel.deleteItemFromNewList(item);
    }



}