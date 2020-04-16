package com.example.postpcapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private ArrayList<Task> tasksArrayList = new ArrayList<>();
    private MainActivity mainActivity;

    TaskAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
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
        holder.textView1.setText(task.getContent());
        holder.imageView.setImageResource(task.isIs_done() ?
                R.drawable.iconfinder_tick : R.drawable.iconfinder_comment);
        holder.textView2.setText(task.isIs_done() ? "" : "" + (position+1));

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.startNewActivity(position);
            }
        });
    }

    void setTasksList(ArrayList<Task> list, boolean dataSetChange){
        tasksArrayList.clear();
        tasksArrayList.addAll(list);
        if (dataSetChange)
            notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasksArrayList.size();
    }
}
