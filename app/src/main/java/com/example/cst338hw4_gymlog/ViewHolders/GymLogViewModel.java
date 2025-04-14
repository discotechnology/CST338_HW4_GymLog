package com.example.cst338hw4_gymlog.ViewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst338hw4_gymlog.database.GymLogRepository;
import com.example.cst338hw4_gymlog.database.entities.GymLog;

import java.util.List;

public class GymLogViewModel extends AndroidViewModel {
    private GymLogRepository repository;

    private final LiveData<List<GymLog>> allLogsByID = null;
    public GymLogViewModel (Application application, int userID) {
        super(application);
        repository = GymLogRepository.getRepository(application);
        allLogsByID = repository.getAllLogsByUserID(userID);
    }

    public LiveData<List<GymLog>> getAllLogsByID() {
        return allLogsByID;
    }
}
