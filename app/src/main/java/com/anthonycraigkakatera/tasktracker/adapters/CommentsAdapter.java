package com.anthonycraigkakatera.tasktracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anthonycraigkakatera.tasktracker.R;
import com.anthonycraigkakatera.tasktracker.model.Comment;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>  {
    private final OnItemClickListener listener;
    private final List<Comment> mValues;
    private Context context;

    public CommentsAdapter(OnItemClickListener listener, List<Comment> mValues, Context context) {
        this.listener = listener;
        this.mValues = mValues;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment_layout, parent, false);
        return new CommentsAdapter.CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comment comment  = mValues.get(position);
        //populating the view
        holder.name.setText(comment.getStaffName());
        holder.date.setText(comment.getDate());
        holder.comment.setText(comment.getComment());
        //bind
        holder.bind(holder, comment, listener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class CommentsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView date;
        public final TextView comment;


        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            comment = (TextView) itemView.findViewById(R.id.comment);
        }

        public void bind(final CommentsViewHolder commentsViewHolder, final Comment comment, final OnItemClickListener listener){}
    }

    public interface OnItemClickListener {}
}
