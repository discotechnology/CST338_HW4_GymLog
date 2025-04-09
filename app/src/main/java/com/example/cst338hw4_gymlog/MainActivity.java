package com.example.cst338hw4_gymlog;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cst338hw4_gymlog.database.GymLogRepository;
import com.example.cst338hw4_gymlog.database.entities.GymLog;
import com.example.cst338hw4_gymlog.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "DAC_GYMLOG";

    String exercise;
    double weight;
    int reps;

    private ActivityMainBinding binding;
    private GymLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new GymLogRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertGymlogRecord();
                updateDisplay();
            }
        });
    }

    private void insertGymlogRecord() {
        GymLog log = new GymLog(exercise, weight, reps);
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
        String currentInfo = binding.logDisplayTextView.getText().toString();
        String newDisplay = String.format(Locale.US, "Exercise: %s%nWeight: %.2f%nReps: %d%n=-=-=-=%n",exercise,weight,reps);
        newDisplay += currentInfo;
        binding.logDisplayTextView.setText(newDisplay);
    }
}