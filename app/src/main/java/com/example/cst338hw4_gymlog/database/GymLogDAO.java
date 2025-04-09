package com.example.cst338hw4_gymlog.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338hw4_gymlog.database.entities.GymLog;

import java.util.ArrayList;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(GymLog gymLog);

    @Query("select * from " + GymLogDatabase.gymLogTable)
    ArrayList<GymLog> getAllRecords();
}
