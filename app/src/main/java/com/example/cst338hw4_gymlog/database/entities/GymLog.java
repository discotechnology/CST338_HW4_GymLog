package com.example.cst338hw4_gymlog.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst338hw4_gymlog.database.GymLogDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = GymLogDatabase.gymLogTable)
public class GymLog {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userID;

    private String exercise;
    private double weight;
    private int reps;

    private LocalDateTime date;

    public GymLog(String exercise, double weight, int reps, int userID) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.userID = userID;

        date = LocalDateTime.now();

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return exercise + '\n' +
                "Weight: " + weight + '\n' +
                "Reps: " + reps + '\n' +
                "Date: " + date.toString() + '\n' +
                "=-=-=-=-=-=-=-=-=-=-=-=" + '\n';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymLog gymLog = (GymLog) o;
        return id == gymLog.id && userID == gymLog.userID && Double.compare(weight, gymLog.weight) == 0 && reps == gymLog.reps && Objects.equals(exercise, gymLog.exercise) && Objects.equals(date, gymLog.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, exercise, weight, reps, date);
    }
}
