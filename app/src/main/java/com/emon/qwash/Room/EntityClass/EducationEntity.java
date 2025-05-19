package com.emon.qwash.Room.EntityClass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "education")
public class EducationEntity {
    @PrimaryKey
    @NonNull
    public String email;

    public String degree;
    public String university;
    public String years;
    public String specialization;
    public long last_updated;


    public EducationEntity(String email, String degree, String university, String years, String specialization, long last_updated) {
        this.email = email;
        this.degree = degree;
        this.university = university;
        this.years = years;
        this.specialization = specialization;
    }
}