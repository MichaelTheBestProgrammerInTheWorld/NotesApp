package com.example.notesapp.user;

import android.app.Application;
import android.os.AsyncTask;

import com.example.notesapp.NoteDatabase;

public class UserRepository {

    private UserDao userDao;
    public static OnSignUpkListener listener;
    public static OnSignInkListener signInkListener;

    public UserRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        userDao = database.userDao();
    }

    public void repoInsertUser(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void repoGetUser(User user) {
        new GetUserAsyncTask(userDao).execute(user);
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;
        private Boolean isNew;

        public InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            User newUser = userDao.getUser(users[0].getUsername(), users[0].getPassword());
            if (newUser == null) {
                userDao.insertUser(users[0]);
                isNew = true;
            } else {
                isNew = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listener.OnSignUp(isNew);
        }
    }

    public interface OnSignUpkListener {
        void OnSignUp(boolean is_new);
    }

    public void setOnSignUpListener(OnSignUpkListener listener) {
        this.listener = listener;
    }

    private static class GetUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;
        User userC;

        public GetUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }


        @Override
        protected Void doInBackground(User... users) {
            userC = userDao.getUser(users[0].getUsername(), users[0].getPassword());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            signInkListener.OnSignIn(userC);
        }
    }

    public interface OnSignInkListener {
        void OnSignIn(User user);
    }

    public void setOnSignInListener(OnSignInkListener listener) {
        this.signInkListener = listener;
    }
}
