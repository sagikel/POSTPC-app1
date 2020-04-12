package com.example.postpcapp1;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private ArrayList<Task> tasksArrayList = new ArrayList<>();
    private Context context;
    private String msg1 = "TODO ";
    private String msg2 = " is now DONE. BOOM!";

    TaskAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.one_tack_item,
                parent,false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskHolder holder, final int position) {
        Task task = tasksArrayList.get(position);
        holder.textView.setText(task.getTask());
        holder.imageView.setImageResource(task.isDone() ?
                R.drawable.checked_checkbox : R.drawable.unchecked_checkbox);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Task.arrayList.get(position).isDone()){
                    Task.arrayList.get(position).setDone(true);
                    holder.imageView.setImageResource(R.drawable.checked_checkbox);
                    Toast.makeText(context,
                            msg1 + Task.arrayList.get(position).getTask() + msg2,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void setTasksList(ArrayList<Task> list){
        tasksArrayList.clear();
        tasksArrayList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasksArrayList.size();
    }
}
