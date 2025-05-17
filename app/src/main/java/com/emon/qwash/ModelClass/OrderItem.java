package com.emon.qwash.ModelClass;

// OrderItem.java
public class OrderItem {
    private String serviceType;
    private String location;
    private String orderTime;
    private String status;
    private String price;

    public OrderItem(String serviceType, String location, String orderTime, String status, String price) {
        this.serviceType = serviceType;
        this.location = location;
        this.orderTime = orderTime;
        this.status = status;
        this.price = price;
    }

    public String getServiceType() { return serviceType; }
    public String getLocation() { return location; }
    public String getOrderTime() { return orderTime; }
    public String getStatus() { return status; }
    public String getPrice() { return price; }
}
