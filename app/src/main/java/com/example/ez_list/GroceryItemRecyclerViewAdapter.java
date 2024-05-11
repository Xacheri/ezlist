package com.example.ez_list;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ez_list.data.Grocery;
import com.example.ez_list.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.ez_list.databinding.FragmentGroceryItemBinding;

import java.util.List;

interface OnDeleteItemClick {
    void OnDeleteItemClick(Grocery item);
}

public class GroceryItemRecyclerViewAdapter extends RecyclerView.Adapter<GroceryItemRecyclerViewAdapter.ViewHolder> {

    private List<Grocery> groceries;


    public OnDeleteItemClick deleteClickListener;


    public GroceryItemRecyclerViewAdapter(List<Grocery> food, OnDeleteItemClick listener) {
        groceries = food;
        deleteClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentGroceryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = groceries.get(position);
        holder.mContentView.setText(groceries.get(position).mName);
    }

    public void updateList(List<Grocery> g) {
        groceries = g;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public ImageButton mDeleteGroceryBtn;
        public Grocery mItem;

        public ViewHolder(FragmentGroceryItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.foodName;
            mDeleteGroceryBtn = binding.deleteFoodButton;
            mDeleteGroceryBtn.setOnClickListener(v -> deleteClickListener.OnDeleteItemClick(mItem));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}