package com.emon.qwash.ModelClass;

import com.google.gson.annotations.SerializedName;

public class ProfileResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Data data;

    public static class Data {
        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("number")
        private String number;

        @SerializedName("image")
        private String image;

        @SerializedName("last_updated")
        private long lastUpdated;

        // Getters
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getNumber() { return number; }
        public String getImage() { return image; }
        public long getLastUpdated() { return lastUpdated; }
    }

    // Getters
    public String getStatus() { return status; }
    public Data getData() { return data; }
}

