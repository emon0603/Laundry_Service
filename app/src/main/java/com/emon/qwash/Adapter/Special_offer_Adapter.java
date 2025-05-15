package com.emon.qwash.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emon.qwash.ModelClass.Special_Offers;
import com.emon.qwash.R;

import java.util.List;

public class Special_offer_Adapter extends RecyclerView.Adapter<Special_offer_Adapter.ViewHolder> {

    List<Special_Offers> itemList;
    int layoutType; // 0 = horizontal, 1 = vertical

    public Special_offer_Adapter(List<Special_Offers> itemList, int layoutType) {
        this.itemList = itemList;
        this.layoutType = layoutType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = (layoutType == 0) ? R.layout.item_offer_horizontal : R.layout.item_offer_vertical;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(itemList.get(position).getImageResId());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImage);
        }
    }
}


