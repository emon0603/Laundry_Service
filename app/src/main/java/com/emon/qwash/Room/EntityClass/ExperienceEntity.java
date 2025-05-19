package com.emon.qwash.Room.EntityClass;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "experience")
public class ExperienceEntity {
    @PrimaryKey
    @NonNull
    public String email;
    public String jobTitle;
    public String hospital;
    public String yearsOfExp;
    public String position;
    public long lastUpdated;

    public ExperienceEntity(
            @NonNull String email,
            String jobTitle,
            String hospital,
            String yearsOfExp,
            String position,
            long lastUpdated
    ) {
        this.email = email;
        this.jobTitle = jobTitle;
        this.hospital = hospital;
        this.yearsOfExp = yearsOfExp;
        this.position = position;
        this.lastUpdated = lastUpdated;
    }
}
