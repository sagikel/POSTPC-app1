package com.example.postpcapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button buttonSave;
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loudData();
        Log.d("MainActivity log",
                "current size of TODOs list: " + Task.taskArrayList.size());

        editText = findViewById(R.id.EditTextToInsert);
        buttonSave = findViewById(R.id.ButtonSave);
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
                    new Task(editText.getText().toString());
                    editText.setText("");
                    taskAdapter.setTasksList(Task.taskArrayList, false);
                    taskAdapter.notifyItemInserted(Task.taskArrayList.size());
                    saveData();
                }
            }
        });
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SP", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Task.taskArrayList);
        editor.putString("Task static list", json);
        editor.apply();
    }

    private void loudData() {
        SharedPreferences sharedPreferences =
                getSharedPreferences("SP", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Task static list", null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> arrayList = gson.fromJson(json, type);
        Task.setArrayTask(arrayList);
    }

    public void removeTODO(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete1))
                .setMessage("\n" + Task.taskArrayList.get(position).getTask() +
                        getString(R.string.delete2))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Task.taskArrayList.remove(position);
                        taskAdapter.setTasksList(Task.taskArrayList, false);
                        taskAdapter.notifyItemRemoved(position);
                        taskAdapter.notifyItemRangeChanged(position,Task.taskArrayList.size());
                         saveData();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_menu_delete)
                .show();
    }

    public void updateTODO(final int position) {
        if (!Task.taskArrayList.get(position).isDone()) {
            Task.taskArrayList.get(position).setDone(true);
            taskAdapter.notifyItemChanged(position);

            Toast.makeText(this,
                    getString(R.string.done1) + Task.taskArrayList.get(position).getTask() +
                            getString(R.string.done2),
                    Toast.LENGTH_LONG).show();
            saveData();
        }
    }
}
