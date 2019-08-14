package com.example.notesapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.MainActivity;
import com.example.notesapp.R;

public class SignInActivity extends AppCompatActivity {

    private EditText username, password;
    private Button signIn;
    private SignInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        viewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        viewModel.setOnSignIn(new SignInViewModel.SignInListener() {
            @Override
            public void userIsNull() {
                Toast.makeText(SignInActivity.this,"User not found!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void userIsNotNull() {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                Toast.makeText(SignInActivity.this,"Signed In Successfully",Toast.LENGTH_LONG).show();
            }
        });
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
                    User user = new User(userName, pass);
                    viewModel.getUser(user);
                }
            }
        });
    }
}
