package com.example.ez_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ez_list.data.Grocery;
import com.example.ez_list.data.GroceryList;

import java.util.List;



public class GroceryListItem extends Fragment   {

    interface OnDeleteClickListener {
        void onDeleteClick(GroceryList list);

    }
    private GroceryListCollectionViewModel groceryListCollectionViewModel;

    public static GroceryListItem newInstance() {
        return new GroceryListItem();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        groceryListCollectionViewModel = new ViewModelProvider(this).get(GroceryListCollectionViewModel.class);

        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }


    // adapting this fragment to the recycling process
    public static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        private List<GroceryList> itemList;
        private Activity host;
        private OnDeleteClickListener onDeleteClickListener;  // Listener for delete clicks

        public ItemAdapter(List<GroceryList> groceryLists, Activity thisActivity, OnDeleteClickListener listener) {
            this.itemList = groceryLists;
            this.host = thisActivity;
            this.onDeleteClickListener = listener;  // Store the passed listener
        }
        public void onOpenListClick(int pos, String listName) {
            Intent i = new Intent(host, ViewListActivity.class);
            i.putExtra("LIST_NAME", listName);
            host.startActivity(i);
        }
        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_list_item, parent, false);
            return new ItemViewHolder(itemView, onDeleteClickListener);  // Pass the listener to the ViewHolder
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int pos) {
            GroceryList list = itemList.get(pos);
            holder.bind(list);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public void updateList(List<GroceryList> groceryLists) {
            itemList = groceryLists;
            notifyDataSetChanged();
        }

        public GroceryList getListItemByPosition(int pos) {
            return itemList.get(pos);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            private TextView itemName;
            private ImageButton deleteButton;
            private ImageButton openButton;

            ItemViewHolder(@NonNull View itemView, OnDeleteClickListener deleteListener) {
                super(itemView);
                itemName = itemView.findViewById(R.id.list_item_name_text);
                deleteButton = itemView.findViewById(R.id.delete_button);
                openButton = itemView.findViewById(R.id.open_list_btn);

                openButton.setOnClickListener(v -> onOpenListClick(getAdapterPosition(), itemName.getText().toString()));

                deleteButton.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        deleteListener.onDeleteClick(itemList.get(position));
                    }
                });
            }

            void bind(GroceryList list) {
                itemName.setText(list.name);
            }
        }
    }

}