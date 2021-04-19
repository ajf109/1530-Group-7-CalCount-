package com.example.calcount;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//database, currently only contains users, will have to be expanded
@Database(entities = {User.class, Food.class, Exercise.class}, version = 2)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;

    public abstract UserDao userDao();
    public abstract FoodDao foodDao();
    public abstract ExerciseDao exerciseDao();

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            //change the "name" in order to create a new database if something went wrong in the one you were using
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class, "user_database13")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
