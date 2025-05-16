package com.emon.qwash.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emon.qwash.ModelClass.AddressItem;
import com.emon.qwash.ModelClass.ServiceItem;
import com.emon.qwash.R;

import java.util.ArrayList;
import java.util.List;

public class Address_Adapter extends RecyclerView.Adapter<Address_Adapter.ViewHolder> {
    private List<String> items;
    private int iconResId;

    public Address_Adapter(List<String> items, int iconResId) {
        this.items = items;
        this.iconResId = iconResId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = items.get(position);
        holder.tvTitle.setText(title);
        holder.ivIcon.setImageResource(iconResId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivIcon;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titleText);
            ivIcon = itemView.findViewById(R.id.leftIcon);
        }
    }
}





