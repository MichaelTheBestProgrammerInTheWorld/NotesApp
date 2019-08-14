package com.example.notesapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.R;

public class SignUpActivity extends AppCompatActivity{

    private EditText username, password, repeatPass;
    private Button signUp;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.setOnSignUpListener(new UserViewModel.SignUpkListener() {
            @Override
            public void isNewUser() {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                Toast.makeText(SignUpActivity.this,"User Added",Toast.LENGTH_LONG).show();
            }

            @Override
            public void isNotNewUser() {
                Toast.makeText(SignUpActivity.this,"User Already Exists",Toast.LENGTH_LONG).show();
            }
        });
        username = findViewById(R.id.sign_up_username);
        password = findViewById(R.id.sign_up_password);
        repeatPass = findViewById(R.id.sign_up_repeat);
        signUp = findViewById(R.id.sign_up_btn);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String repeatP = repeatPass.getText().toString().trim();
                if (userName.isEmpty()|| pass.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"Enter all fields",Toast.LENGTH_LONG).show();
                } else if (!pass.equals(repeatP)) {
                    Toast.makeText(SignUpActivity.this,"Password and repeat password don't match",Toast.LENGTH_LONG).show();
                }
                else {
                    //pass to viewModel
                    User user = new User(userName, pass);
                    userViewModel.insert(user);
                }
            }
        });
    }
}
