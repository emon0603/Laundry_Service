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

import com.emon.qwash.ModelClass.ServiceItem;
import com.emon.qwash.R;

import java.util.List;

public class Services_Explore_Adapter extends RecyclerView.Adapter<Services_Explore_Adapter.ServiceViewHolder> {

    private Context context;
    private List<ServiceItem> serviceList;

    public Services_Explore_Adapter(Context context, List<ServiceItem> serviceList) {
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


    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView imgService;
        TextView tvPrice, tvName;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgService = itemView.findViewById(R.id.imgService);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvName = itemView.findViewById(R.id.tvName);
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



