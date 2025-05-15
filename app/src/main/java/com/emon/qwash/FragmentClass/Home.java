package com.emon.qwash.FragmentClass;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.emon.qwash.Adapter.Special_offer_Adapter;
import com.emon.qwash.ModelClass.Special_Offers;
import com.emon.qwash.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {




    View dot;
    LinearLayout labelContainer;

    RecyclerView recyclerView;
    Special_offer_Adapter adapter;
    List<Special_Offers> itemList;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View viewhome = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = viewhome.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        // Sample Data
        itemList = new ArrayList<>();
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));

        adapter = new Special_offer_Adapter(itemList);
        recyclerView.setAdapter(adapter);


        dot = viewhome.findViewById(R.id.progress_dot);
        labelContainer = viewhome.findViewById(R.id.label_container);

        // Call this with 0, 1, 2 or 3
        updateProgressDot(0 ); // Change this index to test




        return viewhome;
    }


    private void updateProgressDot(int stepIndex) {
        labelContainer.post(() -> {
            TextView label = (TextView) labelContainer.getChildAt(stepIndex);
            int labelCenter = label.getLeft() + label.getWidth() / 2;
            dot.setX(labelCenter - dot.getWidth() / 2f);
        });
    }



}