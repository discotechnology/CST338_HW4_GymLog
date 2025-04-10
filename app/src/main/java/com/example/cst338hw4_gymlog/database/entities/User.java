package com.example.cst338hw4_gymlog.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338hw4_gymlog.database.GymLogDatabase;

@Entity(tableName = GymLogDatabase.USER_TABLE)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;
}
