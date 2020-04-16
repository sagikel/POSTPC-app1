package com.example.postpcapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NotCompletedTodoActivity extends AppCompatActivity {

    int position;
    TextView createTime;
    TextView modifyTime;
    EditText editTODO;
    ImageButton saveButton;
    ImageButton doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_completed_todo);

        Intent intentCreatedMe = getIntent();
        position = intentCreatedMe.getIntExtra("position", -1);

        createTime = findViewById(R.id.CreateDate1);
        modifyTime = findViewById(R.id.ModifyDate1);
        editTODO = findViewById(R.id.editTODO);
        saveButton = findViewById(R.id.UnMarkButton);
        doneButton = findViewById(R.id.DoneButton);

        createTime.setText(String.format("Created: %s",
                Task.taskArrayList.get(position).getCreation_timestamp()));
        modifyTime.setText(String.format("Modified: %s",
                Task.taskArrayList.get(position).getEdit_timestamp()));
        editTODO.setText(Task.taskArrayList.get(position).getContent());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBaseManager.getFireBaseManagerObject().changeTodo(position,
                        editTODO.getText().toString());

                Toast.makeText(getApplicationContext(), "Saved",
                        Toast.LENGTH_SHORT).show();

                MainActivity.changes = true;
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBaseManager.getFireBaseManagerObject().makeTodoDone(position);

                MainActivity.done = true;
                finish();
            }
        });
    }
}
