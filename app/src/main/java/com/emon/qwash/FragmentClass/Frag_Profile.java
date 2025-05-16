package com.emon.qwash.FragmentClass;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emon.qwash.Adapter.Address_Adapter;
import com.emon.qwash.ModelClass.AddressItem;
import com.emon.qwash.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Frag_Profile extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Profimeview = inflater.inflate(R.layout.fragment_frag__profile, container, false);


        RecyclerView address_recyclerView = Profimeview.findViewById(R.id.recyclerView);
        RecyclerView save_card_recyclerView = Profimeview.findViewById(R.id.save_card_recyclerView);

        List<String> addressList = Arrays.asList("Home", "Office");
        List<String> paymentList = Arrays.asList("Visa", "Mastercard");

        Address_Adapter addressAdapter = new Address_Adapter(addressList, R.drawable.ic_location);
        Address_Adapter paymentAdapter = new Address_Adapter(paymentList, R.drawable.ic_payment_card);

        address_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        address_recyclerView.setAdapter(addressAdapter);

        save_card_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        save_card_recyclerView.setAdapter(paymentAdapter);




        return Profimeview;

    }
}