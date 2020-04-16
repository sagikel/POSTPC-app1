package com.example.postpcapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class CompletedTodoActivity extends AppCompatActivity {

    int position;
    TextView createTime;
    TextView modifyTime;
    TextView completedTime;
    TextView TODOView;
    ImageButton deleteButton;
    ImageButton UnMarkButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_todo);

        Intent intentCreatedMe = getIntent();
        position = intentCreatedMe.getIntExtra("position", -1);

        createTime = findViewById(R.id.CreateDate2);
        modifyTime = findViewById(R.id.ModifyDate2);
        completedTime = findViewById(R.id.CompletedDate);
        TODOView = findViewById(R.id.TODOView);
        deleteButton = findViewById(R.id.DeleteButton);
        UnMarkButton = findViewById(R.id.UnMarkButton);
        context = this;

        createTime.setText(String.format("Created: %s",
                Task.taskArrayList.get(position).getCreation_timestamp()));
        modifyTime.setText(String.format("Modified: %s",
                Task.taskArrayList.get(position).getEdit_timestamp()));
        completedTime.setText(String.format("Completed: %s",
                Task.taskArrayList.get(position).getComplete_timestamp()));
        TODOView.setText(Task.taskArrayList.get(position).getContent());

        KonfettiView konfettiView = findViewById(R.id.viewKonfetti);
        konfettiView.build()
                .addColors(0xFF0C6325, Color.BLACK, Color.WHITE)
                .setDirection(0.0, 359.0)
                .setSpeed(5f, 3f)
                .setFadeOutEnabled(true)
                .setTimeToLive(5000L)
                .addShapes(Shape.RECT,Shape.CIRCLE)
                .addSizes(new Size(7, 7))
                .setPosition(-50f, 1000f, -50f, -50f)
                .streamFor(300, 2000L);

        UnMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireBaseManager.getFireBaseManagerObject().unMarkTodo(position);

                Toast.makeText(getApplicationContext(), "Un-Mark TODO - Saved",
                        Toast.LENGTH_SHORT).show();

                MainActivity.changes = true;

                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(getString(R.string.delete1))
                        .setMessage("\n" + Task.taskArrayList.get(position).getContent() +
                                getString(R.string.delete2))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                FireBaseManager.getFireBaseManagerObject().deleteTODO(position);
                                MainActivity.remove = true;
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.iconfinder_bin_empty)
                        .show();
            }
        });
    }
}
