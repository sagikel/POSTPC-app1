package com.example.postpcapp1;

import com.google.firebase.firestore.FirebaseFirestore;

public class FireBaseManager {
    static private FireBaseManager fireBaseManager = new FireBaseManager();
    private FirebaseFirestore DB;
    private FireBaseManager() {
        DB = FirebaseFirestore.getInstance();
    }

    public static FireBaseManager getFireBaseManagerObject(){
        return fireBaseManager;
    }

    public FirebaseFirestore getDB() {
        return DB;
    }

    public void addTODO(String TodoString) {
        // Local
        Task task = new Task(TodoString);
        // Cloud
        DB.collection("Tasks")
                .document("" + task.getId())
                .set(task);
    }

    public void makeTodoDone(int position) {
        // Local
        Task.taskArrayList.get(position).setIs_done(true);
        Task.taskArrayList.get(position).setCompleteToNow();
        // Cloud
        DB.collection("Tasks")
                .document("" + Task.taskArrayList.get(position).getId())
                .update("is_done", true,
                        "complete_timestamp",
                        Task.taskArrayList.get(position).getComplete_timestamp());

    }

    public void changeTodo(int position, String newText) {
        // Local
        Task.taskArrayList.get(position).setContent(newText);
        Task.taskArrayList.get(position).setEditToNow();
        // Cloud
        DB.collection("Tasks")
                .document("" + Task.taskArrayList.get(position).getId())
                .update("content", newText,
                        "edit_timestamp",
                        Task.taskArrayList.get(position).getEdit_timestamp());

    }

    public void unMarkTodo(int position) {
        // Local
        Task.taskArrayList.get(position).setIs_done(false);
        Task.taskArrayList.get(position).setEditToNow();
        // Cloud
        DB.collection("Tasks")
                .document("" + Task.taskArrayList.get(position).getId())
                .update("is_done", false,
                        "edit_timestamp",
                        Task.taskArrayList.get(position).getEdit_timestamp());
    }

    public void deleteTODO(int position) {
        // Cloud
        DB.collection("Tasks")
                .document("" + Task.taskArrayList.get(position).getId())
                .delete();
        // Local
        Task.taskArrayList.remove(position);
    }
}
