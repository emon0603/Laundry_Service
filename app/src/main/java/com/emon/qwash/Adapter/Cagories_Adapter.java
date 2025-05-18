package com.emon.qwash.Adapter;


import android.content.Context;
import android.content.Intent;
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

import com.emon.qwash.ModelClass.ServiceItem;
import com.emon.qwash.R;
import com.emon.qwash.Service_Order.Order_details;

import java.util.List;

public class Cagories_Adapter extends RecyclerView.Adapter<Cagories_Adapter.ServiceViewHolder> {

    private Context context;
    private List<ServiceItem> serviceList;

    public Cagories_Adapter(Context context, List<ServiceItem> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceItem item = serviceList.get(position);

        holder.imgService.setImageResource(item.getImageResId());
        holder.tvPrice.setText(item.getPrice());
        holder.tvName.setText(item.getName());

        setGradientText(holder.tvPrice);


        // ✅ ফেভারিট স্ট্যাটাস অনুযায়ী হার্ট আইকন সেট করুন
        if (item.isFavorite()) {
            holder.favorite_icon.setImageResource(R.drawable.ic_heart);
            holder.favorite_icon.setColorFilter(Color.RED);
        } else {
            holder.favorite_icon.setImageResource(R.drawable.ic_heart_stroke);
        }

        // ✅ ফেভারিট আইকন ক্লিক ইভেন্ট
        holder.favorite_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setFavorite(!item.isFavorite()); // স্ট্যাটাস পরিবর্তন

                // ✅ আবার আইকন পরিবর্তন করুন
                if (item.isFavorite()) {
                    holder.favorite_icon.setImageResource(R.drawable.ic_heart);
                    holder.favorite_icon.setColorFilter(Color.RED);



                } else {
                    holder.favorite_icon.setImageResource(R.drawable.ic_heart_stroke);
                    holder.favorite_icon.setColorFilter(Color.GRAY);
                }
            }
        });

        // ✅ আইটেম ক্লিক করলে স্ট্যাটাস আপডেট
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Order_details.class);
            context.startActivity(intent);
        });



    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView imgService,favorite_icon;
        TextView tvPrice, tvName;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgService = itemView.findViewById(R.id.imgService);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvName = itemView.findViewById(R.id.tvName);
            favorite_icon = itemView.findViewById(R.id.favorite_icon);
        }
    }

    private void setGradientText(TextView textView) {
        Shader shader = new LinearGradient(
                0, 0,
                textView.getPaint().measureText(textView.getText().toString()), 0,
                new int[]{Color.parseColor("#C14DE6"), Color.parseColor("#E379B4"), Color.parseColor("#F38F9B")},
                new float[]{0f, 0.7f,0.7f},
                Shader.TileMode.CLAMP
        );
        textView.getPaint().setShader(shader);
        textView.invalidate();
    }


}



