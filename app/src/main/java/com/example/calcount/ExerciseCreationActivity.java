package com.example.calcount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExerciseCreationActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    String username;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_creation);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(UserViewModel.class);

        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id", -1);

        EditText exNameText = findViewById(R.id.exNameText);
        EditText caloriesText = findViewById(R.id.calsBurnedText);

        Button newExerciseButton = findViewById(R.id.newExerciseButton);
        newExerciseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String name = exNameText.getText().toString();
                String caloriesStr = caloriesText.getText().toString();
                if (!(name.equals("") || caloriesStr.equals("")))
                {
                    int calories = Integer.parseInt(caloriesStr);

                    Exercise exercise = new Exercise(name, calories, id, false);
                    userViewModel.insertExercise(exercise);

                    Intent intent = new Intent(v.getContext(), HomepageActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("id", id);

                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ExerciseCreationActivity.this, "Please enter all fields", Toast.LENGTH_LONG).show();
                }


            }
        });
    }


}