package com.example.cst338hw4_gymlog.Database;

import android.app.Application;
import android.util.Log;

import com.example.cst338hw4_gymlog.Database.Entities.GymLog;
import com.example.cst338hw4_gymlog.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import kotlin.reflect.KCallable;

public class GymLogRepository {

    private GymLogDAO gymLogDAO;
    private ArrayList<GymLog> allLogs;

    public GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getGymLogDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.allLogs = this.gymLogDAO.getAllRecords();
    }

    public ArrayList<GymLog> getAllLogs() {
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(new Callable<ArrayList<GymLog>>() {
            @Override
            public ArrayList<GymLog> call() throws Exception {
                return gymLogDAO.getAllRecords();
            }
        });
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem retrieving GymLogs from repository.");
        }
        return null;
    }
}
