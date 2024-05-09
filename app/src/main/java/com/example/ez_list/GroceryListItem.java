package com.example.ez_list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ez_list.data.GroceryList;

import java.util.List;

public class GroceryListItem extends Fragment {

    private ListItemViewModel mViewModel;

    public static GroceryListItem newInstance() {
        return new GroceryListItem();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }


    // adapting this fragment to the recycling process
    public static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        private List<GroceryList> itemList;

        public interface OnButtonClickListener {
            void onDeleteListClick(int pos);
            void onOpenListClick(int pos);
        }
        public ItemAdapter(List<GroceryList> groceryLists) {
            this.itemList = groceryLists;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_list_item, parent, false);
            return new ItemViewHolder(itemView);
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

        public GroceryList getListItemByPosition(int pos)
        {
            return itemList.get(pos);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            private TextView itemName;

            ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                itemName = itemView.findViewById(R.id.list_item_name_text);
            }

            void bind(GroceryList list) {
                itemName.setText(list.name);
            }
        }
    }


}