package com.example.calcount;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int calories;

    private int userId;

    public Exercise(String name, int calories, int userId) {
        this.name = name;
        this.calories = calories;
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public int getUserId() {
        return userId;
    }
}
