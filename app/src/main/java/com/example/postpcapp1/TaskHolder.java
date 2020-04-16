package com.example.postpcapp1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class TaskHolder extends RecyclerView.ViewHolder {

    TextView textView1;
    TextView textView2;
    ImageView imageView;
    ConstraintLayout constraintLayout;


    TaskHolder(@NonNull View view){
        super(view);
        textView1 = view.findViewById(R.id.taskview);
        textView2 = view.findViewById(R.id.textView1);
        imageView = view.findViewById(R.id.checkbox);
        constraintLayout = view.findViewById(R.id.parent_layout);
    }
}
