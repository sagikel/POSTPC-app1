package com.example.postpcapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String emptyTaskMsg = "You can't create an empty TODO..";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.EditTextToInsert);
        Button buttonSave = findViewById(R.id.ButtonSave);

        final TaskAdapter taskAdapter = new TaskAdapter(getApplicationContext());
        taskAdapter.setTasksList(Task.arrayList);
        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), emptyTaskMsg,
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    new Task(editText.getText().toString());
                    editText.setText("");
                    taskAdapter.setTasksList(Task.arrayList);
                    recyclerView.setAdapter(taskAdapter);
                }
            }
        });
    }
}

