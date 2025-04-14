package com.example.cst338hw4_gymlog.ViewHolders;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst338hw4_gymlog.MainActivity;
import com.example.cst338hw4_gymlog.database.GymLogRepository;
import com.example.cst338hw4_gymlog.database.entities.GymLog;

import java.util.List;

public class GymLogViewModel extends AndroidViewModel {

    private GymLogRepository repository;

    public GymLogViewModel (Application application) {
        super(application);
        repository = GymLogRepository.getRepository(application);
    }

    public void insert(GymLog log) {
        repository.insertGymLog(log);
    }

    public LiveData<List<GymLog>> getAllLogsByID(int userID) {
        return repository.getAllLogsByUserID(userID);
    }
}
