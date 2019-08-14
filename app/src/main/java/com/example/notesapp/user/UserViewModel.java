package com.example.notesapp.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private SignUpkListener listener;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void insert(User user) {
        userRepository.repoInsertUser(user);
        userRepository.setOnSignUpListener(new UserRepository.OnSignUpkListener() {
            @Override
            public void OnSignUp(boolean is_new) {
                if (is_new) {
                    listener.isNewUser();
                } else {
                    listener.isNotNewUser();
                }
            }
        });
    }

    public interface SignUpkListener {
        void isNewUser();

        void isNotNewUser();
    }

    public void setOnSignUpListener(SignUpkListener listener) {
        this.listener = listener;
    }


}
