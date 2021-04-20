package com.example.calcount;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

//implements AddExerciseAdapter.ButtonListener so that it can
//detect button clicks on the list items
public class AddExerciseActivity extends AppCompatActivity implements AddExerciseAdapter.ButtonListener{
    private UserViewModel userViewModel;
    String username;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id", -1);

        //find the recycler view, and then attach the adapter to it
        RecyclerView erv = findViewById(R.id.addExercisesRV);
        erv.setLayoutManager(new LinearLayoutManager(this));
        erv.setHasFixedSize(true);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(UserViewModel.class);

        AddExerciseAdapter exAdapter = new AddExerciseAdapter(this);
        erv.setAdapter(exAdapter);

        //update list of exercises in the adapter when something changes
        userViewModel.getAllExercises(id).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                exAdapter.setExercises(exercises);
            }
        });

    }

    //when one of the buttons in the list is clicked, add the exercise to the diary and return to homepage
    @Override
    public void onAddExClick(Exercise exercise) {
        exercise.setInDiary(true);
        userViewModel.update(exercise);

        Intent intent = new Intent(this, HomepageActivity.class);
        intent.putExtra("username", username);

        startActivity(intent);
    }
}