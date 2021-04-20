package com.example.calcount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

//This activity is started upon launching the app, it gives the user the option
//to log in or register a new account
public class MainActivity extends AppCompatActivity {


    private UserViewModel userViewModel;

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
        setContentView(R.layout.activity_main);

        //UserViewModel is a layer of abstraction used to interact with the Room Database
        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(UserViewModel.class);

        //various buttons/text fields from the xml document
        Button gotoregButton = findViewById(R.id.gotoregButton);
        Button loginButton = findViewById(R.id.loginButton);
        EditText userText = findViewById(R.id.userText);
        EditText passText = findViewById(R.id.passText);


        //attempt to log the user in when they hit the "login" button
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //get info from text fields
                String username = userText.getText().toString();
                String password = passText.getText().toString();

                //attempt to find user with the given username
                User user = userViewModel.get(username);

                //log the user in if the username exists and the password matches
                if (user == null)
                    Toast.makeText(MainActivity.this, "invalid username", Toast.LENGTH_LONG).show();
                else if (password.equals(user.getPassword()))
                {
                    int id = user.getId();

                    Intent intent = new Intent(v.getContext(), HomepageActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("id", id);

                    startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this, "invalid password", Toast.LENGTH_LONG).show();
            }
        });

        //send user to registration page when they click the "registration" button
        gotoregButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegistrationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}