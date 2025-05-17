package com.emon.qwash.ModelClass;

public class OrderDisplayItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ORDER = 1;

    private int type;
    private String date; // for header
    private OrderItem order; // for order item

    public OrderDisplayItem(int type, String date, OrderItem order) {
        this.type = type;
        this.date = date;
        this.order = order;
    }

    public int getType() { return type; }
    public String getDate() { return date; }
    public OrderItem getOrder() { return order; }
}


