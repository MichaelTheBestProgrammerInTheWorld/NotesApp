package com.example.notesapp.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.R;

public class SignInActivity extends AppCompatActivity {

    private EditText username, password;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.sign_in_username);
        password = findViewById(R.id.sign_in_password);
        signIn = findViewById(R.id.sign_in_btn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (userName.isEmpty()|| pass.isEmpty()){
                    Toast.makeText(SignInActivity.this,"Enter all fields",Toast.LENGTH_LONG).show();
                }
                else {
                    //pass to viewModel
                }
            }
        });
    }
}
