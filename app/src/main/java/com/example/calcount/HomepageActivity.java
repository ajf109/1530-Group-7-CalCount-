package com.example.calcount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//this is the page that appears after a user logs in
//nothing really happens here yet, but its where we will
//have food/exercise creation, and the user's diary
public class HomepageActivity extends AppCompatActivity {

    //UserViewModel is a layer of abstraction used to interact with the Room Database
    private UserViewModel userViewModel;
    String username;
    int id;
    double BMI;

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
        double height = user.getHeight();
        double weight = user.getWeight();

        double heightM = height / 3.281;
        double weightM = weight / 2.205;

        BMI = weightM / (heightM * heightM);

        TextView bmiText = findViewById(R.id.bmiText);
        bmiText.setText("" + BMI + "");

        Toast.makeText(HomepageActivity.this, "Welcome, " + username,Toast.LENGTH_LONG).show();
        
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
    }


}