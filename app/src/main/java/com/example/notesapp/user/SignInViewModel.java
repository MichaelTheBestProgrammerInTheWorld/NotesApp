package com.example.notesapp.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SignInViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private SignInListener signInListener;

    public SignInViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void getUser(User user) {
        userRepository.repoGetUser(user);
        userRepository.setOnSignInListener(new UserRepository.OnSignInkListener() {
            @Override
            public void OnSignIn(User user) {
                if (user == null) {
                    signInListener.userIsNull();
                } else {
                    signInListener.userIsNotNull();
                }
            }
        });
    }

    public interface SignInListener {
        void userIsNull();

        void userIsNotNull();
    }

    public void setOnSignIn(SignInListener listener1) {
        this.signInListener = listener1;
    }
}
