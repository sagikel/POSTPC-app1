package com.example.postpcapp1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView textView;
    ImageView imageView;
    ConstraintLayout constraintLayout;

    TaskHolder(@NonNull View view){
        super(view);
        textView = view.findViewById(R.id.task_view);
        imageView = view.findViewById(R.id.checkbox);
        constraintLayout = view.findViewById(R.id.parent_layout);
    }

    @Override
    public void onClick(View v) {

    }
}
