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

public class IncompleteTasksAdapter extends RecyclerView.Adapter<IncompleteTasksAdapter.InompleteTaskViewHolder> {
    private final OnItemClickListener listener;
    private final List<IncompleteTask> mValues;
    private Context context;

    public IncompleteTasksAdapter(OnItemClickListener listener, List<IncompleteTask> mValues, Context context) {
        this.listener = listener;
        this.mValues = mValues;
        this.context = context;
    }

    @NonNull
    @Override
    public InompleteTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_incomplete_task_layout, parent, false);
        return new IncompleteTasksAdapter.InompleteTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InompleteTaskViewHolder holder, int position) {
        IncompleteTask incompleteTask = mValues.get(position);
        //populating the view
        holder.title.setText(incompleteTask.getTitle());
        holder.dueDate.setText(incompleteTask.getDueDate());

        //bind
        holder.bind(holder,incompleteTask, listener);
        System.out.println(" -------------------------------------------------------------------- WELL BINDING");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class InompleteTaskViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView dueDate;
        public final ToggleButton button;

        public InompleteTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            title = (TextView) itemView.findViewById(R.id.title);
            dueDate = (TextView) itemView.findViewById(R.id.dueDate);
            button = (ToggleButton) itemView.findViewById(R.id.statusToggleButton);
        }

        public void bind(final InompleteTaskViewHolder inompleteTaskViewHolder, final IncompleteTask incompleteTask,final OnItemClickListener listener){
            inompleteTaskViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(inompleteTaskViewHolder, incompleteTask);
                }
            });

            inompleteTaskViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickToggle(inompleteTaskViewHolder, incompleteTask);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(InompleteTaskViewHolder inompleteTaskViewHolder, IncompleteTask incompleteTask);
        void onClickToggle(InompleteTaskViewHolder inompleteTaskViewHolder, IncompleteTask incompleteTask);
    }

    public  void refreshList(List<IncompleteTask> newList){
        mValues.clear();
        mValues.addAll(newList);
        notifyDataSetChanged();
    }
}
