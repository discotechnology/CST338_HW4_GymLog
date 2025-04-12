package com.example.cst338hw4_gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.cst338hw4_gymlog.database.GymLogRepository;
import com.example.cst338hw4_gymlog.database.entities.User;
import com.example.cst338hw4_gymlog.databinding.ActivityLoginBinding;
import com.example.cst338hw4_gymlog.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private GymLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()) {
            Toast.makeText(LoginActivity.this, "No username entered", Toast.LENGTH_SHORT).show();
        }
        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if(user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if(password.equals(user.getPassword())) {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(),user.getId()));
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}