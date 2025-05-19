package com.emon.qwash.Room.EntityClass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chamber")
public class ChamberEntity {

    @PrimaryKey
    @NonNull
    public String email;

    public String hospital;
    public String address;
    public String time;
    public String conNumber;

    public long lastUpdated;

    public ChamberEntity(String email, String hospital, String address, String time, String conNumber, long lastUpdated) {
        this.email = email;
        this.hospital = hospital;
        this.address = address;
        this.time = time;
        this.conNumber = conNumber;
        this.lastUpdated = lastUpdated;
    }
}

