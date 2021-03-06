package com.example.calcount;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//contains all data for a user
@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;

    private String password;

    private String email;

    //height is currently a double, so 5 ft 6 inches would be a value of "5.5"
    private double height;

    private double weight;

    private int age;

    private boolean isMale;

    public User(String username, String password, String email, double height, double weight, int age, boolean isMale) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.isMale = isMale;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public boolean isMale() {
        return isMale;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
