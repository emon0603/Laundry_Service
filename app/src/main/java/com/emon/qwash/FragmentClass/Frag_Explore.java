package com.emon.qwash.FragmentClass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emon.qwash.Adapter.Services_Explore_Adapter;
import com.emon.qwash.Adapter.Special_offer_Adapter;
import com.emon.qwash.ModelClass.ServiceItem;
import com.emon.qwash.ModelClass.Special_Offers;
import com.emon.qwash.R;

import java.util.ArrayList;
import java.util.List;

public class Frag_Explore extends Fragment {

    RecyclerView ExploreecyclerView;
    Services_Explore_Adapter adapter;
    List<Special_Offers> itemList;
    List<ServiceItem> services = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Exploreview = inflater.inflate(R.layout.fragment_frag__explore, container, false);

        ExploreecyclerView = Exploreview.findViewById(R.id.ExploreecyclerView);

        ExploreecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns
        ExploreecyclerView.setAdapter(new Services_Explore_Adapter(getContext(), services));


        services = new ArrayList<>();
        services.add(new ServiceItem(R.drawable.service_item_1, "$12 Per Kg", "Regular Wash"));
        services.add(new ServiceItem(R.drawable.service_item_1, "$5 Per Item", "Dry Cleaning"));
        services.add(new ServiceItem(R.drawable.service_item_1, "$3 Per Item", "Wash & Ironing"));
        services.add(new ServiceItem(R.drawable.service_item_1, "Pick Manually", "Service Bundle"));

        adapter = new Services_Explore_Adapter(getContext(), services);
        ExploreecyclerView.setAdapter(adapter);






        return Exploreview;
    }
}