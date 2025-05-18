package com.emon.qwash.NotificationClass;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emon.qwash.Adapter.NotificationAdapter;
import com.emon.qwash.ModelClass.OrderDisplayItem;
import com.emon.qwash.ModelClass.OrderItem;
import com.emon.qwash.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationHistory extends AppCompatActivity {


    RecyclerView recyclerView;
    List<OrderItem> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification_history);
        WindowCompat.getInsetsController(getWindow(),getWindow().getDecorView()).setAppearanceLightStatusBars(false);



        recyclerView = findViewById(R.id.recyclerViewNotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        List<OrderDisplayItem> displayList = NotificationAdapter.getSectionedOrderList(rawOrderList);
        NotificationAdapter adapter = new NotificationAdapter(displayList);
        recyclerView.setAdapter(adapter);

    }


    public void GotoBack(View view) {
        onBackPressed();
    }
}