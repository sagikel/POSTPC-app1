package com.example.postpcapp1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button buttonSave;
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    MainActivity mainActivity;
    boolean appLaunch = true;
    static int position = 0;
    static boolean changes = false;
    static boolean done = false;
    static boolean remove = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        loudData();

        editText = findViewById(R.id.EditTextToInsert);
        buttonSave = findViewById(R.id.ButtonCreate);
        recyclerView = findViewById(R.id.recyclerview);

        taskAdapter = new TaskAdapter(this);
        taskAdapter.setTasksList(Task.taskArrayList, true);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), getString(R.string.emptyTodoMsg),
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    FireBaseManager
                            .getFireBaseManagerObject().addTODO(editText.getText().toString());
                    editText.setText("");
                    taskAdapter.setTasksList(Task.taskArrayList, false);
                    taskAdapter.notifyItemInserted(Task.taskArrayList.size());
                    saveData();
                }
            }
        });
    }

    public void saveData() { // local backup for internet problems
        SharedPreferences sharedPreferences = getSharedPreferences("SP", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Task.taskArrayList);
        editor.putString("Task static list", json);
        editor.apply();
    }

    private void loudData() {
        // local backup for fast launch
        SharedPreferences sharedPreferences =
                getSharedPreferences("SP", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Task static list", null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> arrayList = gson.fromJson(json, type);
        Task.setArrayTask(arrayList);
    }

    public void removeTODO() {
        taskAdapter.setTasksList(Task.taskArrayList, false);
        taskAdapter.notifyItemRemoved(position);
        taskAdapter.notifyItemRangeChanged(position, Task.taskArrayList.size());
    }

    public void updateTODO() {
        Toast.makeText(this,
                getString(R.string.done1) + Task.taskArrayList.get(position).getContent() +
                        getString(R.string.done2),
                Toast.LENGTH_LONG).show();

        KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
        konfettiView.build()
                    .addColors(0xFF0C6325, Color.BLACK, Color.WHITE)
                    .setDirection(0.0, 359.0)
                    .setSpeed(0f, 6f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(5000L)
                    .addShapes(Shape.RECT,Shape.CIRCLE)
                    .addSizes(new Size(8, 20))
                    .setPosition(recyclerView.getX()+recyclerView.getWidth()/2f,
                            recyclerView.getY()+recyclerView.getHeight()/2f)
                    .burst(5000);
    }

    public void startNewActivity(int position) {
        MainActivity.position = position;
        if (Task.taskArrayList.get(position).isIs_done()){
            Intent intent = new Intent(this, CompletedTodoActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, NotCompletedTodoActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (changes) {
            taskAdapter.notifyItemChanged(position);
            changes = false;
            saveData();
        }
        if (done) {
            taskAdapter.notifyItemChanged(position);
            done = false;
            updateTODO();
            saveData();
        }
        if (remove) {
            remove = false;
            removeTODO();
            saveData();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

         FireBaseManager.getFireBaseManagerObject().getDB()
                .collection("Tasks")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e  != null) {
                            Log.w("TAG", "Listen failed.", e);
                            return;
                        }
                        ArrayList<Task> arrayList = new ArrayList<>();
                        assert queryDocumentSnapshots != null;
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Task task = document.toObject(Task.class);
                            arrayList.add(task);
                        }
                        int size = arrayList.size();
                        if (size == Task.taskArrayList.size()) {
                            boolean same = true;
                            for (int i=0; i<size; ++i){
                                if (!arrayList.get(i).compareTasks(Task.taskArrayList.get(i))) {
                                    same = false;
                                    break;
                                }
                            }
                            if (same) {
                                if (appLaunch){
                                    Log.d("MainActivity log",
                                            "current size of TODOs list: "
                                                    + Task.taskArrayList.size());
                                    appLaunch = false;
                                }
                                return;
                            }
                        }

                        // cloud update
                        Task.taskArrayList.clear();
                        Task.taskArrayList.addAll(arrayList);
                        taskAdapter.setTasksList(Task.taskArrayList, true);
                        Toast toast = Toast.makeText(mainActivity, "Cloud Update", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();

                        if (appLaunch){
                            Log.d("MainActivity log",
                                    "current size of TODOs list: " + Task.taskArrayList.size());
                            appLaunch = false;
                        }
                        saveData(); // local backup
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
