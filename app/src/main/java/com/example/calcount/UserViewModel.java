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

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }

    public void insertExercise(Exercise exercise) { repository.insert(exercise); }

    public void insertFood(Food food) { repository.insert(food); }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public User get(String username){ return repository.get(username);}

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
}
