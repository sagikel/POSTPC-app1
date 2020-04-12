package com.example.postpcapp1;

import java.util.ArrayList;

public class Task {

    public static ArrayList<Task> arrayList = new ArrayList<>();
    private String task;
    private boolean done;

    public Task(String description){
        task = description;
        done = false;
        arrayList.add(this);
    }

    public String getTask() {
        return task;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


}
