package com.example.cst338hw4_gymlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.cst338hw4_gymlog.database.GymLogRepository;
import com.example.cst338hw4_gymlog.database.entities.GymLog;
import com.example.cst338hw4_gymlog.database.entities.User;
import com.example.cst338hw4_gymlog.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "DAC_GYMLOG";
    public static final String MAIN_ACTIVITY_USERID = "com.example.cst338hw4_gymlog.MAIN_ACTIVITY_USER_ID";
    public static final String SHARED_PREFERENCE_USERID_KEY = "com.example.cst338hw4_gymlog.SHARED_PREFERENCE_USERID_KEY";
    public static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.cst338hw4_gymlog.SHARED_PREFERENCE_USERID_VALUE";

    private static final int LOGGED_OUT = -1;

    String exercise;
    double weight;
    int reps;
    int loggedInUserID = -1;

    private ActivityMainBinding binding;
    private GymLogRepository repository;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());
        loginUser();

        if(loggedInUserID == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }



        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        updateDisplay();

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertGymlogRecord();
                updateDisplay();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user == null) {
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void insertGymlogRecord() {
        if(exercise.isEmpty()) {
            return;
        }
        GymLog log = new GymLog(exercise, weight, reps, loggedInUserID);
        repository.insertGymLog(log);
    }


    private void getInformationFromDisplay() {

        exercise = binding.exerciseInputEditText.getText().toString();

        try {
            weight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Weight");
        }

        try {
            reps = Integer.parseInt(binding.repsInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from Weight");
        }
    }

    private void updateDisplay() {
        ArrayList<GymLog> allLogs = repository.getAllLogsByUserID(loggedInUserID);
        if(allLogs.isEmpty()) {
            binding.logDisplayTextView.setText(R.string.nothing_to_show_time_to_hit_the_gym);
        }
        StringBuilder sb = new StringBuilder();
        for(GymLog log : allLogs) {
            sb.append(log.toString());
        }
        binding.logDisplayTextView.setText(sb.toString());
    }

    private void loginUser() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_VALUE, LOGGED_OUT);
        if(loggedInUserID != LOGGED_OUT) {
            return;
        }

        loggedInUserID = getIntent().getIntExtra(MAIN_ACTIVITY_USERID, LOGGED_OUT);
        if(loggedInUserID == LOGGED_OUT) {
            return;
        } else {
            LiveData<User> userObserver = repository.getUserByID(loggedInUserID);
            userObserver.observe(this, user -> {
                if(user != null) {
                    this.user = user;
                    invalidateOptionsMenu();
                }
            });
        }
    }

    private void logout () {

        loggedInUserID = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USERID, LOGGED_OUT);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, loggedInUserID);
        sharedPrefEditor.apply();
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setTitle("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();

    }

    static Intent mainActivityIntentFactory(Context context, int userID) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USERID, userID);
        return intent;
    }
}