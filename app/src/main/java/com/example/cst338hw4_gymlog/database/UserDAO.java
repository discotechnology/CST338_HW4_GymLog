package com.example.cst338hw4_gymlog.database;

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
    List<User> getAllUsers();

    @Query("delete from " + GymLogDatabase.USER_TABLE)
    void deleteAll();
}
