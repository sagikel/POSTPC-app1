package com.example.postpcapp1;

import java.util.ArrayList;

public class Task {

    static ArrayList<Task> taskArrayList = new ArrayList<>();
    private String task;
    private boolean done;

    public Task(String description){
        task = description;
        done = false;
        taskArrayList.add(this);
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

    public static void setArrayTask(ArrayList<Task> arrayList){
        if (arrayList == null)
            return;
        taskArrayList.clear();
        taskArrayList.addAll(arrayList);
    }
}
