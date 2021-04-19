package com.example.calcount;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


public class AddFoodActivity extends AppCompatActivity implements AddFoodAdapter.ButtonListener{
    private UserViewModel userViewModel;
    String username;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id", -1);

        RecyclerView rv = findViewById(R.id.addFoodsRV);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);//can get rid?

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(UserViewModel.class);

        AddFoodAdapter foodAdapter = new AddFoodAdapter(username, this);
        rv.setAdapter(foodAdapter);

        userViewModel.getAllFoods(id).observe(this, new Observer<List<Food>>() {
           @Override
           public void onChanged(@Nullable List<Food> foods) {
               foodAdapter.setFoods(foods);
           }
        });
    }

    @Override
    public void onAddFoodClick(Food food) {
        food.setInDiary(true);
        userViewModel.update(food);

        Intent intent = new Intent(this, HomepageActivity.class);
        intent.putExtra("username", username);

        startActivity(intent);
    }
}