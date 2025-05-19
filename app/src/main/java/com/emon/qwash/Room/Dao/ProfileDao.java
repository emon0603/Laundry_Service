package com.emon.qwash.Room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emon.qwash.Room.EntityClass.ProfileEntity;


@Dao
public interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProfile(ProfileEntity profile);

    @Update
    void updateProfile(ProfileEntity profile);

    @Query("SELECT * FROM profiles WHERE email = :email")
    ProfileEntity getProfileByEmail(String email);
}