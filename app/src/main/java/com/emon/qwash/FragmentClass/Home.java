package com.emon.qwash.FragmentClass;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
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

    TextView heretexttv;
    View dot,progressLine;
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
        heretexttv = viewhome.findViewById(R.id.heretexttv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        // Sample Data
        itemList = new ArrayList<>();
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));
        itemList.add(new Special_Offers(R.drawable.banner_special_offer));

        adapter = new Special_offer_Adapter(itemList,0);
        recyclerView.setAdapter(adapter);


        dot = viewhome.findViewById(R.id.progress_dot);
        labelContainer = viewhome.findViewById(R.id.label_container);
        progressLine = viewhome.findViewById(R.id.progress_line);

        // Call this with 0, 1, 2 or 3
        updateProgressDot(4); // Change this index to test

        setGradientText(heretexttv);


        return viewhome;
    }


    private void updateProgressDot(int stepIndex) {
        labelContainer.post(() -> {

            if (progressLine == null) return;

            if (stepIndex == 0) {
                // Dot at the very start
                float offset = dpToPx(10); // safe offset so it's not cut off
                int lineStartX = progressLine.getLeft();
                dot.setX(lineStartX - dot.getWidth() / 2f + offset);
            } else if (stepIndex == 4) {
                // Dot at the very end
                float offset = dpToPx(10); // safe offset so it doesn't overflow
                int lineEndX = progressLine.getRight();
                dot.setX(lineEndX - dot.getWidth() / 2f - offset);
            } else {
                // Dot in the middle (based on label)
                int labelCount = labelContainer.getChildCount();
                if (stepIndex - 1 < 0 || stepIndex - 1 >= labelCount) return;

                TextView label = (TextView) labelContainer.getChildAt(stepIndex - 1);
                int labelCenter = label.getLeft() + label.getWidth() / 2;
                dot.setX(labelCenter - dot.getWidth() / 2f);
            }
        });
    }



    private float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    private void setGradientText(TextView textView) {
        Shader shader = new LinearGradient(
                0, 0,
                textView.getPaint().measureText(textView.getText().toString()), 0,
                new int[]{Color.parseColor("#5F4BFF"), Color.parseColor("#5F4BFF"), Color.parseColor("#9E5CFF")},
                new float[]{0f, 0.7f,0.7f},
                Shader.TileMode.CLAMP
        );
        textView.getPaint().setShader(shader);
        textView.invalidate();
    }





}