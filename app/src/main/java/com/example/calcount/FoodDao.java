package com.example.calcount;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert
    void insert(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Query("UPDATE food_table SET inDiary = 0")
    void clearFood();

    @Query("SELECT * FROM food_table WHERE userId = :user_id AND NOT inDiary ORDER BY id DESC")
    LiveData<List<Food>> getAllFoods(int user_id);

    @Query("SELECT * FROM food_table WHERE userId = :user_id AND inDiary ORDER BY id DESC")
    LiveData<List<Food>> getAllDiaryFoods(int user_id);
}
