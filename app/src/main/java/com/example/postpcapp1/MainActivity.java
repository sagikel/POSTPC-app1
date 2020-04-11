package com.example.postpcapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String msg1 = "Task ";
    int counter = 1;
    String msg2 = ": \n\t\t\t\t";
    ArrayList<String> toDoListArray = new ArrayList<>();

    /**
     * Make updated StringBuilder to textViewToPerform.
     * @return StringBuilder of all Tasks.
     */
    StringBuilder showTasks(){
        StringBuilder tasks = new StringBuilder();
        for (String s : toDoListArray)
            tasks.append(s);
        return tasks;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textViewToPerform = findViewById(R.id.TextViewToPerform);
        final EditText editText = findViewById(R.id.EditTextToInsert);
        Button buttonSave = findViewById(R.id.ButtonSave);
        textViewToPerform.setMovementMethod(new ScrollingMovementMethod());

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                toDoListArray.add(msg1 + counter + msg2 + editText.getText() + "\n");
                textViewToPerform.setText(showTasks());
                editText.setText("");
                counter++;
            }
        });
    }
}

