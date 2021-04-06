package com.example.calcount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FoodCreationActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    String username;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_creation);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(UserViewModel.class);

        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id", -1);

        EditText foodNameText = findViewById(R.id.foodNameText);
        EditText caloriesText = findViewById(R.id.foodCalsText);

        Button newFoodButton = findViewById(R.id.newFoodButton);
        newFoodButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String name = foodNameText.getText().toString();
                String caloriesStr = caloriesText.getText().toString();

                //check if empty first
                int calories = Integer.parseInt(caloriesStr);

                Food food = new Food(name, calories, id);
                userViewModel.insertFood(food);

                Intent intent = new Intent(v.getContext(), HomepageActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("id", id);

                startActivity(intent);
            }
        });
    }
}