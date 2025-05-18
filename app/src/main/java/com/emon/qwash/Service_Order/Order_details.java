package com.emon.qwash.Service_Order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import com.emon.qwash.R;
import com.emon.qwash.util.WhiteStatusBar;
import com.google.android.material.button.MaterialButton;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Order_details extends AppCompatActivity {

    private SeekBar spicySeekBar;
    private MaterialButton btnUp, btnDown,pricetv,order_btn;
    private TextView quantity_item_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_details);

        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);

        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        quantity_item_tv = findViewById(R.id.quantity_item_tv);
        spicySeekBar = findViewById(R.id.spicySeekBar);
        pricetv = findViewById(R.id.pricetv);
        order_btn = findViewById(R.id.order_btn);

        // ✅ Set default value to 1
        spicySeekBar.setProgress(1);
        quantity_item_tv.setText("1");

        float price = 8.24f;
        float total_price = price * 1;
        BigDecimal bd = new BigDecimal(total_price).setScale(2, RoundingMode.HALF_UP);
        pricetv.setText("$" + bd.floatValue());

        order_btn.setEnabled(true); // Default e 1 thakle enabled thakbe

        btnUp.setOnClickListener(v -> {
            int progress = spicySeekBar.getProgress();
            if (progress < spicySeekBar.getMax()) {
                spicySeekBar.setProgress(progress + 1);
                quantity_item_tv.setText(String.valueOf(progress + 1));
            }
        });

        btnDown.setOnClickListener(v -> {
            int progress = spicySeekBar.getProgress();
            if (progress > 0) {
                spicySeekBar.setProgress(progress - 1);
                quantity_item_tv.setText(String.valueOf(progress - 1));
            }
        });

        spicySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float progress_int = Float.parseFloat(String.valueOf(progress));
                float total_price = price * progress_int;

                BigDecimal bd = new BigDecimal(total_price).setScale(2, RoundingMode.HALF_UP);
                float final_price = bd.floatValue();

                Log.d("TAG", "onProgressChanged: " + final_price);
                pricetv.setText("$" + final_price);

                if (progress == 0) {
                    order_btn.setEnabled(false);
                } else {
                    order_btn.setEnabled(true);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Order_details.this, Order_payment.class));
            }
        });

    }


    public void GotoBack(View view) {
        onBackPressed();
    }

}