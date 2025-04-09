package com.example.cst338hw4_gymlog.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.cst338hw4_gymlog.Database.Entities.GymLog;

@Database(entities = {GymLog.class}, version = 1, exportSchema = false)
public abstract class GymLogDatabase extends RoomDatabase {
}
