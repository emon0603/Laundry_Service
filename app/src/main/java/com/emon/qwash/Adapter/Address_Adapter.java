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
    private List<AddressItem> addressList;
    private int iconResId;

    public Address_Adapter( List<AddressItem> addressList,int iconResId) {
        this.addressList = addressList;
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

        AddressItem item = addressList.get(position);
        holder.tvTitle.setText( item.type.toUpperCase());
        holder.addressTV.setText( item.address);


    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, addressTV;
        ImageView ivIcon;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titleText);
            addressTV = itemView.findViewById(R.id.addressTV);
            ivIcon = itemView.findViewById(R.id.leftIcon);
        }
    }
}





