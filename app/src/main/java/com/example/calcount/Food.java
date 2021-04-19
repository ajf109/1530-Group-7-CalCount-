package com.example.calcount;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_table")
public class Food {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    private int calories;
    private int carbs;
    private int proteins;
    private int fats;

    private int userId;

    private boolean inDiary;

    public Food(String name, int calories, int carbs, int proteins, int fats,
                int userId, boolean inDiary) {
        this.name = name;
        this.calories = calories;
        this.userId = userId;
        this.inDiary = inDiary;
        this.carbs = carbs;
        this.proteins = proteins;
        this.fats = fats;
    }

    public boolean isInDiary() {
        return inDiary;
    }

    public void setInDiary(boolean inDiary) {
        this.inDiary = inDiary;
    }

    public int getUserId() {
        return userId;
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

    public int getCarbs() { return carbs; }

    public int getProteins() { return proteins; }

    public int getFats() { return fats; }

    public void setId(int id) {
        this.id = id;
    }
}
