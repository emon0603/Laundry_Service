package com.emon.qwash.Room.EntityClass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profiles")
public class ProfileEntity {
    @PrimaryKey
    @NonNull
    public String email;

    public String name;
    public String number;
    public String imageUrl;
    public long lastUpdated;

    public ProfileEntity(
            @NonNull String email,
            String name,
            String number,
            String imageUrl,
            long lastUpdated
    ) {
        this.email = email;
        this.name = name;
        this.number = number;
        this.imageUrl = imageUrl;
        this.lastUpdated = lastUpdated;
    }
}