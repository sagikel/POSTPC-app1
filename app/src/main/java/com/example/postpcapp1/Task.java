package com.example.postpcapp1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Task {

    static ArrayList<Task> taskArrayList = new ArrayList<>();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd-MMM-yyyy HH:mm aa", Locale.getDefault());
    private String content;
    private boolean is_done;
    private long id;
    private String creation_timestamp;
    private String edit_timestamp;
    private String complete_timestamp;

    public Task(){}

    public Task(String description) {
        content = description;
        is_done = false;
        creation_timestamp = simpleDateFormat.format(new Date());
        id = new Date().getTime();
        edit_timestamp = creation_timestamp;
        complete_timestamp = "Not yet";

        taskArrayList.add(this);
    }

    // Time setters
    public void setEditToNow() {
        edit_timestamp = simpleDateFormat.format(new Date());
    }

    public void setCompleteToNow() {
        complete_timestamp = simpleDateFormat.format(new Date());
    }

    // setters
    public void setContent(String content) {
        this.content = content;
    }

    public void setIs_done(boolean done) {
        this.is_done = done;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreation_timestamp(String creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public void setEdit_timestamp(String edit_timestamp) {
        this.edit_timestamp = edit_timestamp;
    }

    public void setComplete_timestamp(String complete_timestamp) {
        this.complete_timestamp = complete_timestamp;
    }

    // getters
    public String getContent() {
        return content;
    }

    public boolean isIs_done() {
        return is_done;
    }

    public long getId() {
        return id;
    }

    public String getCreation_timestamp() {
        return creation_timestamp;
    }

    public String getEdit_timestamp() {
        return edit_timestamp;
    }

    public String getComplete_timestamp() {
        return complete_timestamp;
    }


    boolean compareTasks(Task task) {
        return this.id == task.id &&
                this.content.equals(task.content) &&
                (this.is_done == task.is_done) &&
                this.creation_timestamp.equals(task.creation_timestamp) &&
                this.edit_timestamp.equals(task.edit_timestamp) &&
                this.complete_timestamp.equals(task.complete_timestamp);
    }

    // static
    public static void setArrayTask(ArrayList<Task> arrayList){
        if (arrayList == null)
            return;
        taskArrayList.clear();
        taskArrayList.addAll(arrayList);
    }
}
