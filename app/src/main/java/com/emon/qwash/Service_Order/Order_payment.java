package com.emon.qwash.Service_Order;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.emon.qwash.MainActivity;
import com.emon.qwash.R;
import com.google.android.material.button.MaterialButton;

public class Order_payment extends AppCompatActivity {

    MaterialButton go_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Payment_select();

    }

    private void Payment_select() {

        boolean ischecked = true;

        RadioButton radio1 = findViewById(R.id.radio1);
        RadioButton radio2 = findViewById(R.id.radio2);
        RadioButton radio3 = findViewById(R.id.radio3);
        LinearLayout layout1 = findViewById(R.id.layout1);
        LinearLayout layout2 = findViewById(R.id.layout2);
        LinearLayout layout3 = findViewById(R.id.layout3);
        TextView credit_card_tv = findViewById(R.id.credit_card_tv);
        TextView debit_card_tv = findViewById(R.id.debit_card_tv);
        TextView cashondelivery_tv = findViewById(R.id.cashondelivery_tv);

// Default: radio2 selected
        radio1.setChecked(true);
        layout1.setBackgroundResource(R.drawable.bg_selected);
        layout2.setBackgroundResource(R.drawable.bg_unselected);
        layout3.setBackgroundResource(R.drawable.bg_unselected);
        credit_card_tv.setTextColor(getResources().getColor(R.color.white));

        radio1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radio2.setChecked(false);
                radio3.setChecked(false);

                layout1.setBackgroundResource(R.drawable.bg_selected);
                layout2.setBackgroundResource(R.drawable.bg_unselected);
                layout3.setBackgroundResource(R.drawable.bg_unselected);

                credit_card_tv.setTextColor(getResources().getColor(R.color.white));
                debit_card_tv.setTextColor(getResources().getColor(R.color.black));
                cashondelivery_tv.setTextColor(getResources().getColor(R.color.black));
            }
        });

        radio2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radio1.setChecked(false);
                radio3.setChecked(false);

                layout2.setBackgroundResource(R.drawable.bg_selected);
                layout1.setBackgroundResource(R.drawable.bg_unselected);
                layout3.setBackgroundResource(R.drawable.bg_unselected);

                debit_card_tv.setTextColor(getResources().getColor(R.color.white));
                credit_card_tv.setTextColor(getResources().getColor(R.color.black));
                cashondelivery_tv.setTextColor(getResources().getColor(R.color.black));
            }
        });

        radio3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radio1.setChecked(false);
                radio2.setChecked(false);

                layout3.setBackgroundResource(R.drawable.bg_selected);
                layout1.setBackgroundResource(R.drawable.bg_unselected);
                layout2.setBackgroundResource(R.drawable.bg_unselected);

                cashondelivery_tv.setTextColor(getResources().getColor(R.color.white));
                credit_card_tv.setTextColor(getResources().getColor(R.color.black));
                debit_card_tv.setTextColor(getResources().getColor(R.color.black));
            }
        });




    }

    private void showCustomDialog() {
        Dialog dialog = new Dialog(Order_payment.this);
        dialog.setContentView(R.layout.item_payment_status);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        go_back_btn = dialog.findViewById(R.id.go_back_btn);

        go_back_btn.setOnClickListener(v -> {
            startActivity(new Intent(Order_payment.this, MainActivity.class));
            finish();
            dialog.dismiss();
        });

        dialog.show();
    }

    public void back_btn_details(View view) {
        onBackPressed();
    }

    public void Pay_Btn(View view) {
        showCustomDialog();
    }


}