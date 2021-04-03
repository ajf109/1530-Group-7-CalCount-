package com.example.calcount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//this is the page where a user enters information in order to create a new account
public class RegistrationActivity extends AppCompatActivity {

    //UserViewModel is a layer of abstraction used to interact with the Room Database
    private UserViewModel userViewModel;

    //various buttons/text fields from the xml document
    RadioGroup genderGroup;
    RadioButton maleButton;
    RadioButton femaleButton;
    RadioButton radioButton;
    EditText weightText;
    EditText heightText;
    EditText ageText;

    //always go back to MainActivity when hitting the back button to prevent weird things from happening
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(UserViewModel.class);

        //various buttons/text fields from the xml document
        Button regButton = findViewById(R.id.regButton);
        maleButton = findViewById(R.id.maleButton);
        femaleButton = findViewById(R.id.femaleButton);
        EditText userText = findViewById(R.id.userRegText);
        EditText passText = findViewById(R.id.passRegText);
        EditText emailText = findViewById(R.id.emailRegText);
        weightText = findViewById(R.id.weightText);
        heightText = findViewById(R.id.heightText);
        ageText = findViewById(R.id.ageText);
        genderGroup = findViewById(R.id.genderRadio);

        //attempt to register a new account when the user hits the registration button
        regButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                //get info from various text fields
                String username = userText.getText().toString();
                String password = passText.getText().toString();
                String email = emailText.getText().toString();
                String weightStr = weightText.getText().toString();
                String heightStr = heightText.getText().toString();
                String ageStr = ageText.getText().toString();

                //get id of the radiobutton that has been selected (either male/female)
                int genderId = genderGroup.getCheckedRadioButtonId();

                //only create an account if all fields have data in them
                if (!(genderId == -1 || username.equals("") || password.equals("") || email.equals("")
                || weightStr.equals("") || heightStr.equals("") || ageStr.equals(""))) {


                    double weight = Double.parseDouble(weightStr);
                    double height = Double.parseDouble(heightStr);
                    int age = Integer.parseInt(ageStr);

                    radioButton = findViewById(genderId);
                    boolean isMale = radioButton.getText().equals("Male");

                    //see if a user with the given username already exists
                    User user = userViewModel.get(username);


                    //create a new user if the username is not taken
                    if (user == null) {
                        user = new User(username, password, email, height, weight, age, isMale);
                        userViewModel.insert(user);
                        Intent intent = new Intent(v.getContext(), HomepageActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegistrationActivity.this, "This username has been taken", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(RegistrationActivity.this, "Please enter all fields", Toast.LENGTH_LONG).show();
            }
        });
    }

}

