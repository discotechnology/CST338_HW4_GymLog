package com.example.cst338hw4_gymlog.Database;

import android.app.Application;

import com.example.cst338hw4_gymlog.Database.Entities.GymLog;

import java.util.ArrayList;

public class GymLogRepository {

    private GymLogDAO gymLogDAO;
    private ArrayList<GymLog> allLogs;

    public GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getGymLogDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
    }
}
