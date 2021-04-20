package com.example.calcount;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//the viewmodel is used within the activities to interact with the database
//doing it this way means we are following the MVVM architectural pattern (model view view-model)
public class UserViewModel extends AndroidViewModel {

    private UserDao userDao;
    private FoodDao foodDao;
    private ExerciseDao exerciseDao;

    public UserViewModel(@NonNull Application application) {
        super(application);

        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        foodDao = database.foodDao();
        exerciseDao = database.exerciseDao();
    }

    public void insertExercise(Exercise exercise) {
        exerciseDao.insert(exercise); }

    public void insertFood(Food food) {
        foodDao.insert(food); }

    public void insert(User user) {
        userDao.insert(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void update(Food food) {
        foodDao.update(food);
    }

    public void update(Exercise exercise) {
        exerciseDao.update(exercise);
    }

    public User get(String username){
        return userDao.get(username); }

    public void clearDiary()
    {
        foodDao.clearFood();
        exerciseDao.clearExercise();
    }

    public LiveData<List<Food>> getAllFoods(int user_id) {
        return foodDao.getAllFoods(user_id);
    }

    public LiveData<List<Food>> getAllDiaryFoods(int user_id) { return foodDao.getAllDiaryFoods(user_id);}

    public LiveData<List<Exercise>> getAllExercises(int user_id) {
        return exerciseDao.getAllExercises(user_id);
    }

    public LiveData<List<Exercise>> getAllDiaryExercises(int user_id) {
        return exerciseDao.getAllDiaryExercises(user_id);
    }
}
