package com.example.cst338hw4_gymlog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338hw4_gymlog.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    String exercise;
    double weight;
    int reps;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
            }
        });
    }

    private void getInformationFromDisplay() {

        exercise = binding.exerciseInputEditText.getText().toString();

        try {
            weight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("DAC_GYMLOG", "Error reading value from Weight");
        }

        try {
            weight = Integer.parseInt(binding.repsInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("DAC_GYMLOG", "Error reading value from Weight");
        }
    }
}