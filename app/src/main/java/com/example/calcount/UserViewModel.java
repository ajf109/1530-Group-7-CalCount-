package com.example.calcount;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//layer of abstraction on top of the repository
//used within the activities to interact with the database
//doing it this way means we are following the MVVM architectural pattern (model view view-model)
public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private LiveData<List<User>> allUsers;
    //testing
    private UserDao userDao;
    private FoodDao foodDao;
    private ExerciseDao exerciseDao;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
        //testing
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        foodDao = database.foodDao();
        exerciseDao = database.exerciseDao();
    }

    public void insertExercise(Exercise exercise) { //repository.insert(exercise);
        exerciseDao.insert(exercise); }

    public void insertFood(Food food) { //repository.insert(food);
        foodDao.insert(food); }

    public void insert(User user) {
       // repository.insert(user);
        userDao.insert(user);
    }

    public void update(User user) {
       // repository.update(user);
        userDao.update(user);
    }

    public void update(Food food) {
        foodDao.update(food);
    }

    public void update(Exercise exercise) {
        exerciseDao.update(exercise);
    }

    public void delete(User user) {
       // repository.delete(user);
        userDao.delete(user);
    }

    public User get(String username){ //return repository.get(username);
        return userDao.get(username); }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
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
