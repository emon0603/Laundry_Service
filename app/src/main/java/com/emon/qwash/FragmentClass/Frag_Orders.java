package com.emon.qwash.FragmentClass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emon.qwash.Adapter.SectionedOrderAdapter;
import com.emon.qwash.ModelClass.OrderDisplayItem;
import com.emon.qwash.ModelClass.OrderItem;
import com.emon.qwash.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Frag_Orders extends Fragment {

    RecyclerView recyclerView;
    List<OrderItem> orderList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View Orderview = inflater.inflate(R.layout.fragment_frag__orders, container, false);


        recyclerView = Orderview.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<OrderItem> rawOrderList = new ArrayList<>();
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "16 May 2025, 1:48 PM", "Completed", "30"));
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "16 May 2025, 2:48 PM", "Completed", "30"));
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "16 May 2025, 3:48 PM", "Completed", "30"));
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "16 May 2025, 3:48 PM", "Completed", "30"));
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "15 May 2025, 3:48 PM", "Completed", "30"));
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "15 May 2025, 4:48 PM", "Completed", "30"));
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "15 May 2025, 5:48 PM", "Completed", "30"));
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "14 May 2025, 8:48 PM", "Completed", "30"));
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "14 May 2025, 7:48 PM", "Completed", "30"));
        rawOrderList.add(new OrderItem("Wash", "IIITD, New Delhi", "14 May 2025, 5:48 PM", "Completed", "30"));

        List<OrderDisplayItem> displayList = SectionedOrderAdapter.getSectionedOrderList(rawOrderList);
        SectionedOrderAdapter adapter = new SectionedOrderAdapter(displayList);
        recyclerView.setAdapter(adapter);






        return Orderview;
    }

    private List<OrderDisplayItem> getSectionedOrderList(List<OrderItem> orders) {
        List<OrderDisplayItem> sectionedList = new ArrayList<>();
        Map<String, List<OrderItem>> groupedMap = new LinkedHashMap<>();

        for (OrderItem order : orders) {
            String dateKey = order.getOrderTime();
            if (!groupedMap.containsKey(dateKey)) {
                groupedMap.put(dateKey, new ArrayList<>());
            }
            groupedMap.get(dateKey).add(order);
        }

        for (Map.Entry<String, List<OrderItem>> entry : groupedMap.entrySet()) {
            sectionedList.add(new OrderDisplayItem(OrderDisplayItem.TYPE_HEADER, entry.getKey(), null));
            for (OrderItem o : entry.getValue()) {
                sectionedList.add(new OrderDisplayItem(OrderDisplayItem.TYPE_ORDER, null, o));
            }
        }

        return sectionedList;
    }


}