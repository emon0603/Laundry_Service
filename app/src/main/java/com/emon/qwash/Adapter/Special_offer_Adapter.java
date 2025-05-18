package com.emon.qwash.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emon.qwash.ModelClass.Special_Offers;
import com.emon.qwash.R;
import com.emon.qwash.Service_Order.Order_details;

import java.util.List;

public class Special_offer_Adapter extends RecyclerView.Adapter<Special_offer_Adapter.ViewHolder> {

    List<Special_Offers> itemList;
    int layoutType; // 0 = horizontal, 1 = vertical

    private Context context;

    public Special_offer_Adapter(Context context,List<Special_Offers> itemList, int layoutType) {
        this.context = context;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int layout = (layoutType == 0) ? R.layout.item_offer_horizontal : R.layout.item_offer_vertical;

                if (layout == R.layout.item_offer_horizontal) {
                    Toast.makeText(context, "test1", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "test2", Toast.LENGTH_SHORT).show();
                }
            }
        });

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


