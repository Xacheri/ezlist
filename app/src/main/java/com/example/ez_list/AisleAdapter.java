package com.example.ez_list;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ez_list.data.Aisle;

import java.util.Collections;
import java.util.List;

public class AisleAdapter extends RecyclerView.Adapter<AisleAdapter.AisleViewHolder> implements AisleMoveCallback.ItemTouchHelperContract {

    private List<Aisle> data;

    public OnAisleOrderChangeListener mListener;

    public interface OnAisleOrderChangeListener {
        void OnAisleOrderChangeListener(List<Aisle> data);
    }



    public class AisleViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        View rowView;

        public AisleViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;
            mTitle = itemView.findViewById(R.id.aislename);
        }
    }

    public AisleAdapter(List<Aisle> data, OnAisleOrderChangeListener listener) {
        this.data = data;
        this.mListener = listener;
    }

    @Override
    public AisleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_aisle, parent, false);
        return new AisleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AisleViewHolder holder, int position) {
        Aisle aisle = data.get(position);
        String aisleName = aisle.mName;

        holder.mTitle.setText(aisleName);
    }


    @Override
    public int getItemCount() {
        if(data == null) {
            return 0;
        }
        return data.size();
    }


    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                data.get(i).mIndex = i + 1;
                data.get(i + 1).mIndex = i;
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {

                data.get(i).mIndex = i - 1;
                data.get(i - 1).mIndex = i;
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(AisleViewHolder myViewHolder) {
        myViewHolder.mTitle.setBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(AisleViewHolder myViewHolder) {
        myViewHolder.mTitle.setBackgroundColor(Color.WHITE);
        mListener.OnAisleOrderChangeListener(this.data);
    }

    public void updateData(List<Aisle> aisles){
        this.data = aisles;
        notifyDataSetChanged();
    }
}

