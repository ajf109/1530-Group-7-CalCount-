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
//nothing really happens here yet, but its where we will
//have food/exercise creation, and the user's diary
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

        RecyclerView foodRV = findViewById(R.id.foodDiaryRV);
        foodRV.setLayoutManager(new LinearLayoutManager(this));
        foodRV.setHasFixedSize(true); //get rid of this?
        DiaryFoodAdapter diaryAdapter = new DiaryFoodAdapter(this);
        foodRV.setAdapter(diaryAdapter);

        RecyclerView exRV = findViewById(R.id.exerciseDiaryRV);
        exRV.setLayoutManager(new LinearLayoutManager(this));
        exRV.setHasFixedSize(true); //get rid of this?
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
        bmiText.setText(String.valueOf(BMI).substring(0, 4));

        TextView calsRemaining = findViewById(R.id.calsText);
        calsRemaining.setText(String.valueOf(calories));

        userViewModel.getAllDiaryFoods(id).observe(this, new Observer<List<Food>>(){
            @Override
            public void onChanged(@Nullable List<Food> diaryFoodList) {
                diaryAdapter.setFoods(diaryFoodList);
                foodDiary = diaryFoodList;

                int cals = calories;

                for (int i = 0; i < diaryFoodList.size(); i++)
                {
                    cals = cals - diaryFoodList.get(i).getCalories();
                }
                if (exerciseDiary != null) {
                    for (int i = 0; i < exerciseDiary.size(); i++) {
                        cals = cals + exerciseDiary.get(i).getCalories();
                    }
                }

                calsRemaining.setText("" + cals + "");
            }
        });

        userViewModel.getAllDiaryExercises(id).observe(this, new Observer<List<Exercise>>(){
            @Override
            public void onChanged(@Nullable List<Exercise> diaryExList) {
                diaryExAdapter.setExercises(diaryExList);
                exerciseDiary = diaryExList;

                int cals = calories;

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

        //Toast.makeText(HomepageActivity.this, "" + BMI + "",Toast.LENGTH_LONG).show();
        
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