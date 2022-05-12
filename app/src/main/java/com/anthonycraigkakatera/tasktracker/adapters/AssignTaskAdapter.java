package com.anthonycraigkakatera.tasktracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anthonycraigkakatera.tasktracker.R;
import com.anthonycraigkakatera.tasktracker.model.GeneralTask;

import java.util.List;

public class AssignTaskAdapter extends RecyclerView.Adapter<AssignTaskAdapter.AssignTaskViewHolder> {
    private final OnItemClickListener listener;
    private final List<GeneralTask> mValues;
    private Context context;

    public AssignTaskAdapter(OnItemClickListener listener, List<GeneralTask> mValues, Context context) {
        this.listener = listener;
        this.mValues = mValues;
        this.context = context;
    }

    @NonNull
    @Override
    public AssignTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task_assignment_layout, parent, false);
        return new AssignTaskAdapter.AssignTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignTaskViewHolder holder, int position) {
        GeneralTask generalTask = mValues.get(position);
        //populating the view
        holder.title.setText(generalTask.getTitle());
        holder.dueDate.setText(generalTask.getDueDate());
        //bind
        holder.bind(holder,generalTask, listener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class AssignTaskViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView dueDate;

        public AssignTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            title = (TextView) itemView.findViewById(R.id.title);
            dueDate = (TextView) itemView.findViewById(R.id.dueDate);
        }

        public void bind(final AssignTaskViewHolder assignTaskViewHolder,final GeneralTask generalTask, OnItemClickListener listener){
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(assignTaskViewHolder, generalTask);
                }
            });
        };
    }

    public interface OnItemClickListener {
        void onClick(AssignTaskViewHolder assignTaskViewHolder, GeneralTask generalTask);
    }
}
