package com.example.calcount;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

//this is the page that appears after a user logs in
//it contains their diary, macronutrient information, and button which lead to other activities

//implements both DiaryFoodAdapter.ButtonListener and DiaryExAdapter.ButtonListender, allowing for button presses on either diary to be recognized
public class HomepageActivity extends AppCompatActivity implements DiaryFoodAdapter.ButtonListener, DiaryExAdapter.ButtonListener {

    //UserViewModel is a layer of abstraction used to interact with the Room Database
    private UserViewModel userViewModel;
    String username;
    int id, calories;
    double BMI, BMR;
    List<Exercise> exerciseDiary;
    List<Food> foodDiary;

    //prevent weird things from happening when the user hits the back button by just closing the app
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //link appropriate adapters to their recycler views to display food/exercise entries in diary
        RecyclerView foodRV = findViewById(R.id.foodDiaryRV);
        foodRV.setLayoutManager(new LinearLayoutManager(this));
        foodRV.setHasFixedSize(true);
        DiaryFoodAdapter diaryAdapter = new DiaryFoodAdapter(this);
        foodRV.setAdapter(diaryAdapter);

        RecyclerView exRV = findViewById(R.id.exerciseDiaryRV);
        exRV.setLayoutManager(new LinearLayoutManager(this));
        exRV.setHasFixedSize(true);
        DiaryExAdapter diaryExAdapter = new DiaryExAdapter(this);
        exRV.setAdapter(diaryExAdapter);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(UserViewModel.class);


        //get some basic user info from the intent that we can use to make sure exercises/activities
        //are linked to the correct user
        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id", -1);

        if (id == -1)
        {
            User u = userViewModel.get(username);
            id = u.getId();
        }

        //calculate BMI, recommended calories/macronutrients
        User user = userViewModel.get(username);
        double age = user.getAge();
        double height = user.getHeight();
        double weight = user.getWeight();

        double heightM = height / 3.281;
        double weightM = weight / 2.205;

        BMI = weightM / (heightM * heightM);
        BMR = (10 * (weightM)) + (6.25 * (heightM * 100)) - (5 * (age));
        if (user.isMale()) {
            BMR += 5;
        } else {
            BMR -= 161;
        }

        calories = (int)(BMR * 1.375); // hardcoded activity multiplier (generally 1.2-1.95)

        TextView bmiText = (TextView) findViewById(R.id.bmiText);
        TextView calsRemaining = findViewById(R.id.calsText);
        TextView carbsText = findViewById(R.id.carbsTextView);
        TextView proteinsText = findViewById(R.id.proteinsTextView);
        TextView fatsText = findViewById(R.id.fatsTextView);

        //change color of BMI depending on range
        if (BMI < 18.5)
            bmiText.setTextColor(android.graphics.Color.BLUE);
        else if (BMI < 25)
            bmiText.setTextColor(android.graphics.Color.GREEN);
        else
            bmiText.setTextColor(android.graphics.Color.RED);

        bmiText.setText(String.valueOf(BMI).substring(0, 4));
        calsRemaining.setTextColor(android.graphics.Color.DKGRAY);
        calsRemaining.setText(String.valueOf(calories));

        //update calorie/macronutrient totals each time a food is added to the diary
        userViewModel.getAllDiaryFoods(id).observe(this, new Observer<List<Food>>(){
            @Override
            public void onChanged(@Nullable List<Food> diaryFoodList) {
                diaryAdapter.setFoods(diaryFoodList);
                foodDiary = diaryFoodList;

                int cals = calories;

                // 0 = carbs, 1 = proteins, 2 = fats
                int macros[] = new int[3];

                for (int i = 0; i < diaryFoodList.size(); i++) {
                    cals = cals - diaryFoodList.get(i).getCalories();
                    macros[0] = macros[0] + diaryFoodList.get(i).getCarbs();
                    macros[1] = macros[1] + diaryFoodList.get(i).getProteins();
                    macros[2] = macros[2] + diaryFoodList.get(i).getFats();
                }
                if (exerciseDiary != null) {
                    for (int i = 0; i < exerciseDiary.size(); i++) {
                        cals = cals + exerciseDiary.get(i).getCalories();
                    }
                }

                // Macronutrient calculations below
                carbsText.setText(macros[0] + "g");
                proteinsText.setText(macros[1] + "g");
                fatsText.setText(macros[2] + "g");

                double recCarbsLow = calories * 0.45;
                double recCarbsHigh = calories * 0.65;
                int carbCals = macros[0] * 4; // 4 calories per gram of carb
                if (carbCals > recCarbsLow && carbCals < recCarbsHigh) {
                    carbsText.setTextColor(android.graphics.Color.GREEN);
                } else if (carbCals < recCarbsLow) {
                    carbsText.setTextColor(android.graphics.Color.DKGRAY);
                } else {
                    carbsText.setTextColor(android.graphics.Color.RED);
                }

                double recProteinsLow = calories * 0.12;
                double recProteinsHigh = calories * 0.20;
                int proteinCals = macros[1] * 4; // 4 calories per gram of protein
                if (proteinCals > recProteinsLow && carbCals < recProteinsHigh) {
                    proteinsText.setTextColor(android.graphics.Color.GREEN);
                } else if (proteinCals < recProteinsLow) {
                    proteinsText.setTextColor(android.graphics.Color.DKGRAY);
                } else {
                    proteinsText.setTextColor(android.graphics.Color.RED);
                }

                double recFatsLow = calories * 0.20;
                double recFatsHigh = calories * 0.35;
                int fatCals = macros[2] * 9; // 9 calories per gram of fat
                if (fatCals > recFatsLow && fatCals < recFatsHigh) {
                    fatsText.setTextColor(android.graphics.Color.GREEN);
                } else if (fatCals < recCarbsLow) {
                    fatsText.setTextColor(android.graphics.Color.DKGRAY);
                } else {
                    fatsText.setTextColor(android.graphics.Color.RED);
                }

                calsRemaining.setText("" + cals + "");
            }
        });

        //increase the number of allowed calories when an exercise item is added to the diary
        userViewModel.getAllDiaryExercises(id).observe(this, new Observer<List<Exercise>>(){
            @Override
            public void onChanged(@Nullable List<Exercise> diaryExList) {
                diaryExAdapter.setExercises(diaryExList);
                exerciseDiary = diaryExList;

                int cals = calories;

                // 0 = carbs, 1, proteins, 2 = fats
                int macros[] = new int[3];

                for (int i = 0; i < diaryExList.size(); i++)
                {
                    cals = cals + diaryExList.get(i).getCalories();
                }
                if (foodDiary != null) {
                    for (int i = 0; i < foodDiary.size(); i++) {
                        cals = cals - foodDiary.get(i).getCalories();
                    }
                }

                calsRemaining.setText("" + cals + "");
            }
        });
        
        Button logoutButton = findViewById(R.id.logoutButton);

        //logs the user out and takes them back to MainActivity
        logoutButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button foodButton = findViewById(R.id.foodCreateButton);
        foodButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), FoodCreationActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button exButton = findViewById(R.id.exCreateButton);
        exButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), ExerciseCreationActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button editDetailsButton = findViewById(R.id.editDetailsButton);
        editDetailsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), EditDetailsActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button addFoodButton = findViewById(R.id.addFoodButton);
       addFoodButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), AddFoodActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button addExButton = findViewById(R.id.addExerciseButton);
        addExButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), AddExerciseActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button clearAllButton = findViewById(R.id.clearAllButton);
        clearAllButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                userViewModel.clearDiary();
            }
        });
    }


    @Override
    public void onRemoveFoodClick(Food food) {
        food.setInDiary(false);
        userViewModel.update(food);
    }

    @Override
    public void onRemoveExClick(Exercise exercise) {
        exercise.setInDiary(false);
        userViewModel.update(exercise);
    }
}