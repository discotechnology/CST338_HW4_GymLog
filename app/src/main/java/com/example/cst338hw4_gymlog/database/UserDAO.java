package com.example.cst338hw4_gymlog.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst338hw4_gymlog.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("select * from " + GymLogDatabase.USER_TABLE + " order by username")
    LiveData<List<User>> getAllUsers();

    @Query("delete from " + GymLogDatabase.USER_TABLE)
    void deleteAll();

    @Query("select * from " + GymLogDatabase.USER_TABLE + " where username == :username")
    LiveData<User> getUserByUsername(String username);

    @Query("select * from " + GymLogDatabase.USER_TABLE + " where id == :userID")
    LiveData<User> getUserByID(int userID);
}
