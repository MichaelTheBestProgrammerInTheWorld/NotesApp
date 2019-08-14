package com.example.notesapp.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);
    @Query("SELECT * from user_table where username = :username and password = :password")
    User getUser(String username,String password);
}
