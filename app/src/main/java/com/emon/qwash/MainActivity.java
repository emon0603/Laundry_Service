package com.emon.qwash;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.emon.qwash.FragmentClass.Frag_Explore;
import com.emon.qwash.FragmentClass.Frag_Offers;
import com.emon.qwash.FragmentClass.Frag_Orders;
import com.emon.qwash.FragmentClass.Frag_Profile;
import com.emon.qwash.FragmentClass.Home;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    LinearLayout tabHome, tabOrders, tabExplore,tabOffers, tabProfile;
    ImageView iconHome, iconOrders, iconOffers, iconProfile;
    TextView textHome, textOrders, textOffers, textProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        WindowCompat.getInsetsController(getWindow(),getWindow().getDecorView()).setAppearanceLightStatusBars(false);



        // Initialize the views
        tabHome = findViewById(R.id.tabHome);
        tabOrders = findViewById(R.id.tabOrders);
        tabExplore = findViewById(R.id.tabExplore);
        tabOffers = findViewById(R.id.tabOffers);
        tabProfile = findViewById(R.id.tabProfile);

        iconHome = findViewById(R.id.iconHome);
        iconOrders = findViewById(R.id.iconOrders);
        iconOffers = findViewById(R.id.iconOffers);
        iconProfile = findViewById(R.id.iconProfile);

        textHome = findViewById(R.id.textHome);
        textOrders = findViewById(R.id.textOrders);
        textOffers = findViewById(R.id.textOffers);
        textProfile = findViewById(R.id.textProfile);

        // Initial tab
        setSelectedTab("home");

        // Tab click listeners
        tabHome.setOnClickListener(v -> setSelectedTab("home"));
        tabOrders.setOnClickListener(v -> setSelectedTab("orders"));
        tabExplore.setOnClickListener(v -> setSelectedTab("explore"));
        tabOffers.setOnClickListener(v -> setSelectedTab("offers"));
        tabProfile.setOnClickListener(v -> setSelectedTab("profile"));


        StatusBar();


    }

    private void setSelectedTab(String tab) {
        // Reset all icons and text colors to unselected state
        resetToGray(textHome);
        resetToGray(textOrders);
        resetToGray(textOffers);
        resetToGray(textProfile);

        iconHome.setImageResource(R.drawable.ic_home_unselected);
        iconOrders.setImageResource(R.drawable.ic_order_unselected);
        iconOffers.setImageResource(R.drawable.ic_home_unselected);
        iconProfile.setImageResource(R.drawable.ic_profile_unselected);


        // Set selected tab
        if (tab.equals("home")) {
            iconHome.setImageResource(R.drawable.ic_home_selected);
            setGradientText(textHome);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Home()).commit();

        } else if (tab.equals("orders")) {
            iconOrders.setImageResource(R.drawable.ic_order_selected);
            setGradientText(textOrders);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Frag_Orders()).commit();

        } else if (tab.equals("explore")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Frag_Explore()).commit();


        } else if (tab.equals("offers")) {
            iconOffers.setImageResource(R.drawable.ic_order_selected);
            setGradientText(textOffers);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Frag_Offers()).commit();


        } else if (tab.equals("profile")) {
            iconProfile.setImageResource(R.drawable.ic_profile_selected);
            setGradientText(textProfile);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new Frag_Profile()).commit();

        }
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

    private void resetToGray(TextView textView) {
        // Remove gradient shader
        textView.getPaint().setShader(null);
        textView.setTextColor(Color.parseColor("#9E9E9E"));
        textView.invalidate(); // force redraw
    }

    public void StatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // Set the status bar transparent to show layout background
            window.setStatusBarColor(Color.TRANSPARENT);

            // Ensure white icons by NOT using LIGHT_STATUS_BAR
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
            }
        }
    }




}
