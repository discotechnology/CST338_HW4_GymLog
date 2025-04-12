package com.example.cst338hw4_gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst338hw4_gymlog.database.GymLogRepository;
import com.example.cst338hw4_gymlog.database.entities.User;
import com.example.cst338hw4_gymlog.databinding.ActivityLoginBinding;

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
                if(!verifyUser()){
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()) {
            Toast.makeText(LoginActivity.this, "No username entered", Toast.LENGTH_SHORT).show();
            return false;
        }
        User user = repository.getUserByUsername(username);

        if(user == null) {
            return false;
        }

        String password = binding.passwordLoginEditText.getText().toString();
        return user.getPassword().equals(password);
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}