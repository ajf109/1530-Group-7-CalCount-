package com.example.calcount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FoodCreationActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    String username;
    int id;
    private int calories, carbs, proteins, fats;

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
        EditText carbsText = findViewById(R.id.foodCarbsText);
        EditText proteinsText = findViewById(R.id.foodProteinsText);
        EditText fatsText = findViewById(R.id.foodFatsText);

        Button newFoodButton = findViewById(R.id.newFoodButton);
        newFoodButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String name = foodNameText.getText().toString();
                try {
                    calories = Integer.parseInt(caloriesText.getText().toString());
                } catch(Exception e) { calories = -1; }
                try {
                    carbs = Integer.parseInt(carbsText.getText().toString());
                } catch(Exception e) { carbs = -1; }
                try {
                    proteins = Integer.parseInt(proteinsText.getText().toString());
                } catch(Exception e) { proteins = -1; }
                try {
                    fats = Integer.parseInt(fatsText.getText().toString());
                } catch(Exception e) { fats = -1; }

                //only process if all fields have valid data in them
                if (!(name.equals("") || calories == -1 || carbs == -1
                        || proteins == -1 || fats == -1)) {
                    Food food = new Food(name, calories, carbs, proteins, fats, id, false);
                    userViewModel.insertFood(food);

                    Intent intent = new Intent(v.getContext(), HomepageActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("id", id);

                    startActivity(intent);
                }
                else
                    Toast.makeText(FoodCreationActivity.this,
                            "Please enter all fields\nMacro-nutrients are are whole number " +
                                    "percent values\n in the range 0-99", Toast.LENGTH_LONG).show();

                //Food food = new Food(name, calories, id, false);
                //userViewModel.insertFood(food);


            }
        });
    }
}