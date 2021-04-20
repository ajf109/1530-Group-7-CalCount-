package com.example.calcount;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//data access object, used to interact with the user entities in the database
@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user_table WHERE username = :username")
    User get(String username);

}
