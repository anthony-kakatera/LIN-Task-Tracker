package com.anthonycraigkakatera.tasktracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anthonycraigkakatera.tasktracker.R;
import com.anthonycraigkakatera.tasktracker.model.CompleteTask;
import com.anthonycraigkakatera.tasktracker.model.StaffMember;

import java.util.List;

public class StaffMemberAdapter extends RecyclerView.Adapter<StaffMemberAdapter.StaffMemberViewHolder>{
    private final OnItemClickListener listener;
    private final List<StaffMember> mValues;
    private Context context;

    public StaffMemberAdapter(OnItemClickListener listener, List<StaffMember> mValues, Context context) {
        this.listener = listener;
        this.mValues = mValues;
        this.context = context;
    }

    @NonNull
    @Override
    public StaffMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person_task_assign_layout, parent, false);
        return new StaffMemberAdapter.StaffMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffMemberViewHolder holder, int position) {
        StaffMember staffMember = mValues.get(position);
        //populating the view
        holder.name.setText(staffMember.getName());
        //bind
        holder.bind(holder,staffMember, listener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class StaffMemberViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;

        public StaffMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = (TextView) itemView.findViewById(R.id.name);
        }

        public void bind(final StaffMemberViewHolder staffMemberViewHolder, final StaffMember staffMember, OnItemClickListener listener){
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(staffMemberViewHolder, staffMember);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(StaffMemberViewHolder staffMemberViewHolder, StaffMember staffMember);
    }
}
