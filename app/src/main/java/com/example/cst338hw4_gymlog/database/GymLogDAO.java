package com.example.cst338hw4_gymlog.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338hw4_gymlog.database.entities.GymLog;

import java.util.List;

@Dao
public interface GymLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(GymLog gymLog);

    @Query("select * from " + GymLogDatabase.gymLogTable + " order by date desc")
    List<GymLog> getAllRecords();

    @Query("select * from " + GymLogDatabase.gymLogTable + " where userID == :loggedInUserID order by date desc")
    List<GymLog> getRecordsByUserID(int loggedInUserID);
}
