package com.emon.qwash.FragmentClass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emon.qwash.Adapter.Special_offer_Adapter;
import com.emon.qwash.ModelClass.Special_Offers;
import com.emon.qwash.R;

import java.util.ArrayList;
import java.util.List;


public class Frag_Offers extends Fragment {


    RecyclerView WelcomerecyclerView,recyclerView;
    Special_offer_Adapter adapter;
    List<Special_Offers> welcomeitemList;
    List<Special_Offers> itemList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewOffer = inflater.inflate(R.layout.fragment_frag__offers, container, false);

        WelcomerecyclerView = viewOffer.findViewById(R.id.WelcomerecyclerView);
        recyclerView = viewOffer.findViewById(R.id.recyclerViews);

        welcomeitemList = new ArrayList<>();
        welcomeitemList.add(new Special_Offers(R.drawable.banner2));
        welcomeitemList.add(new Special_Offers(R.drawable.banner2));
        welcomeitemList.add(new Special_Offers(R.drawable.banner2));



        itemList = new ArrayList<>();
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));

        // Horizontal RecyclerView
        WelcomerecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        Special_offer_Adapter horizontalAdapter = new Special_offer_Adapter(welcomeitemList, 1);
        WelcomerecyclerView.setAdapter(horizontalAdapter);

        // Vertical RecyclerView
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        Special_offer_Adapter verticalAdapter = new Special_offer_Adapter(itemList, 1);
        recyclerView.setAdapter(verticalAdapter);

        return viewOffer;
    }



}