package com.emon.qwash.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.emon.qwash.ModelClass.OrderDisplayItem;
import com.emon.qwash.ModelClass.OrderItem;
import com.emon.qwash.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SectionedOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderDisplayItem> displayList;

    public SectionedOrderAdapter(List<OrderDisplayItem> displayList) {
        this.displayList = displayList;
    }

    @Override
    public int getItemViewType(int position) {
        return displayList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == OrderDisplayItem.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
            return new OrderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrderDisplayItem item = displayList.get(position);

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).textDate.setText(item.getDate());
        } else if (holder instanceof OrderViewHolder) {
            OrderItem order = item.getOrder();
            OrderViewHolder vh = (OrderViewHolder) holder;

            vh.textServiceType.setText(order.getServiceType());
            vh.textLocation.setText(order.getLocation());
            vh.textTime.setText(order.getOrderTime());
            vh.textStatus.setText(order.getStatus());
            vh.textPrice.setText("₹ " + order.getPrice());
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textDate;

        HeaderViewHolder(View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDateHeader);
        }
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textServiceType, textLocation, textTime, textStatus, textPrice;

        OrderViewHolder(View itemView) {
            super(itemView);
            textServiceType = itemView.findViewById(R.id.textServiceType);
            textLocation = itemView.findViewById(R.id.textLocation);
            textTime = itemView.findViewById(R.id.textTime);
            textStatus = itemView.findViewById(R.id.textStatus);
            textPrice = itemView.findViewById(R.id.textPrice);
        }
    }

    public static List<OrderDisplayItem> getSectionedOrderList(List<OrderItem> orders) {
        List<OrderDisplayItem> sectionedList = new ArrayList<>();
        Map<String, List<OrderItem>> groupedMap = new LinkedHashMap<>();

        SimpleDateFormat fullDateTimeFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        for (OrderItem order : orders) {
            try {
                Date parsedDate = fullDateTimeFormat.parse(order.getOrderTime());
                String dateKey = dateOnlyFormat.format(parsedDate);

                if (!groupedMap.containsKey(dateKey)) {
                    groupedMap.put(dateKey, new ArrayList<>());
                }
                groupedMap.get(dateKey).add(order);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for (Map.Entry<String, List<OrderItem>> entry : groupedMap.entrySet()) {
            String rawDate = entry.getKey();
            String headerLabel = getFriendlyDate(rawDate);

            sectionedList.add(new OrderDisplayItem(OrderDisplayItem.TYPE_HEADER, headerLabel, null));
            for (OrderItem order : entry.getValue()) {
                sectionedList.add(new OrderDisplayItem(OrderDisplayItem.TYPE_ORDER, null, order));
            }
        }

        return sectionedList;
    }

    private static String getFriendlyDate(String rawDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            Date inputDate = sdf.parse(rawDate);

            Calendar orderCal = Calendar.getInstance();
            orderCal.setTime(inputDate);

            Calendar today = Calendar.getInstance();

            if (isSameDay(orderCal, today)) return "Today";

            today.add(Calendar.DATE, -1);
            if (isSameDay(orderCal, today)) return "Yesterday";

            return rawDate;

        } catch (ParseException e) {
            e.printStackTrace();
            return rawDate;
        }
    }

    private static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
