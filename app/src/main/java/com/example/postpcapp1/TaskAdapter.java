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
        holder.textView.setText(task.getTask());
        holder.imageView.setImageResource(task.isDone() ?
                R.drawable.checked_checkbox : R.drawable.unchecked_checkbox);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.updateTODO(position);
            }
        });

        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mainActivity.removeTODO(position);
                notifyDataSetChanged();
                return false;
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
