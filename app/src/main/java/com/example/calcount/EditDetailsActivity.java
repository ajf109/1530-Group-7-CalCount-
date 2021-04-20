package com.example.calcount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDetailsActivity extends AppCompatActivity {

    String username;
    int id;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(UserViewModel.class);

        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id", -1);

        EditText newHeightText = findViewById(R.id.newHeightText);
        EditText newWeightText = findViewById(R.id.newWeightText);

        Button confirmChangeButton = findViewById(R.id.confirmChangeButton);
        confirmChangeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                User user = userViewModel.get(username);

                String newHeightStr = newHeightText.getText().toString();
                String newWeightStr = newWeightText.getText().toString();

                if (!(newHeightStr.equals("") || newWeightStr.equals("")))
                {
                    double newHeight = Double.parseDouble(newHeightStr);
                    double newWeight = Double.parseDouble(newWeightStr);

                    user.setHeight(newHeight);
                    user.setWeight(newWeight);

                    userViewModel.update(user);

                    Intent intent = new Intent(v.getContext(), HomepageActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("id", id);

                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(EditDetailsActivity.this, "Please enter all fields", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
}