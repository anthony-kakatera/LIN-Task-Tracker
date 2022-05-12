package com.anthonycraigkakatera.tasktracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anthonycraigkakatera.tasktracker.R;
import com.anthonycraigkakatera.tasktracker.model.CompleteTask;
import com.anthonycraigkakatera.tasktracker.model.IncompleteTask;

import java.util.List;

public class CompleteTasksAdapter  extends RecyclerView.Adapter<CompleteTasksAdapter.CompleteTaskViewHolder>{
    private final OnItemClickListener listener;
    private final List<CompleteTask> mValues;
    private Context context;

    public CompleteTasksAdapter(OnItemClickListener listener, List<CompleteTask> mValues, Context context) {
        this.listener = listener;
        this.mValues = mValues;
        this.context = context;
    }

    @NonNull
    @Override
    public CompleteTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_complete_task_layout, parent, false);
        return new CompleteTasksAdapter.CompleteTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompleteTaskViewHolder holder, int position) {
        CompleteTask completeTask = mValues.get(position);
        //populating the view
        holder.title.setText(completeTask.getTitle());
        holder.dueDate.setText(completeTask.getDueDate());

        //bind
        holder.bind(holder,completeTask, listener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class CompleteTaskViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView dueDate;
        public final ToggleButton button;

        public CompleteTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            title = (TextView) itemView.findViewById(R.id.title);
            dueDate = (TextView) itemView.findViewById(R.id.dueDate);
            button = (ToggleButton) itemView.findViewById(R.id.statusToggleButton);
        }

        public void bind(final CompleteTaskViewHolder completeTaskViewHolder, final CompleteTask completeTask, final OnItemClickListener listener){
            completeTaskViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(completeTaskViewHolder, completeTask);
                }
            });

            completeTaskViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickToggle(completeTaskViewHolder, completeTask);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(CompleteTasksAdapter.CompleteTaskViewHolder completeTaskViewHolder, CompleteTask completeTask);
        void onClickToggle(CompleteTasksAdapter.CompleteTaskViewHolder completeTaskViewHolder, CompleteTask completeTask);
    }
}
