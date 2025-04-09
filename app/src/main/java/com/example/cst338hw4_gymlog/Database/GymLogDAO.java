package com.example.cst338hw4_gymlog.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338hw4_gymlog.Database.Entities.GymLog;

import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(GymLog gymLog);

    @Query("select * from " + GymLogDatabase.gymLogTable)
    List<GymLog> getAllRecords();
}
