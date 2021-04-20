package com.example.calcount;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Query("UPDATE exercise_table SET inDiary = 0")
    void clearExercise();

    @Query("SELECT * FROM exercise_table WHERE userId = :user_id AND NOT inDiary ORDER BY id DESC")
    LiveData<List<Exercise>> getAllExercises(int user_id);

    @Query("SELECT * FROM exercise_table WHERE userId = :user_id AND inDiary ORDER BY id DESC")
    LiveData<List<Exercise>> getAllDiaryExercises(int user_id);
}
