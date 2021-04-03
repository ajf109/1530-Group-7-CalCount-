package com.example.calcount;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

//layer of abstraction on top of the DAO.  Isn't really necessary but the tutorial I was
//following said it was good practice to do this
public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
    }

    public void insert(User user)
    {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user)
    {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user)
    {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public User get(String username) {
        return userDao.get(username);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }
}
