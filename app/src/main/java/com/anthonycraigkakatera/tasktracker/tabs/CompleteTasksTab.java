package com.anthonycraigkakatera.tasktracker.tabs;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;
import static com.anthonycraigkakatera.tasktracker.MainActivity.yourLoginDetails;
import static com.anthonycraigkakatera.tasktracker.ViewActivity.intentKey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anthonycraigkakatera.tasktracker.R;
import com.anthonycraigkakatera.tasktracker.ViewActivity;
import com.anthonycraigkakatera.tasktracker.adapters.CompleteTasksAdapter;
import com.anthonycraigkakatera.tasktracker.model.CompleteTask;
import com.anthonycraigkakatera.tasktracker.model.IncompleteTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompleteTasksTab extends Fragment {
    private int mColumnCount = 1;
    private static final String ARG_COLUMN_COUNT = "column-count";
    public CompleteTasksAdapter.OnItemClickListener clickListener;
    public CompleteTasksAdapter adapter;
    private List<CompleteTask> completeTaskList = new ArrayList<>();

    public static View heldView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflating the general view
        View view = inflater.inflate(R.layout.fragment_complete_tasks_layout, container, false);
        //getting the recycler view and inflating it
        RecyclerView recycler = view.findViewById(R.id.completedTasksRecyclerView);
        // Set the adapter
        if (recycler instanceof RecyclerView) {
            Context context = recycler.getContext();
            RecyclerView recyclerView = (RecyclerView) recycler;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //setting the recycler view and adapter
            setRecyclerAdapter(recyclerView, recycler);
        }else{

        }
        heldView = view;
        return view;
    }

    public void setRecyclerAdapter(RecyclerView recyclerView, View view){
        clickListener = new CompleteTasksAdapter.OnItemClickListener() {
            @Override
            public void onClick(CompleteTasksAdapter.CompleteTaskViewHolder completeTaskViewHolder, CompleteTask completeTask) {
                //called when the whole view is clicked
                //here we open a new intent
                openActivity(completeTask);
            }

            @Override
            public void onClickToggle(CompleteTasksAdapter.CompleteTaskViewHolder completeTaskViewHolder, CompleteTask completeTask) {
                if(completeTaskViewHolder.button.isChecked()){
                    //toggle on
                    toggleOn(completeTaskViewHolder, completeTask);
                    //adjusting the status at the backend
                    toggleStatus(completeTask, "2");
                    //rfresh list

                }else{
                    //toggle off
                    toggleOff(completeTaskViewHolder, completeTask);
                    //adjusting the status at the backend
                    toggleStatus(completeTask, "1");
                    //rfresh list

                }
            }
        };
        //downloading json data via volley api and then update interface upon completion
        downloadContent(recyclerView, clickListener);
    }

    private void downloadContent(RecyclerView recyclerView, CompleteTasksAdapter.OnItemClickListener clickListener) {
        String url = mainUrl + "getCompleteTasks.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> processAllTasksRetrieval(response, recyclerView, clickListener),
                error -> Toast.makeText(getContext(), "Task assignment failed", Toast.LENGTH_LONG).show()){
            //Request parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("staff_id", yourLoginDetails.getId());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    private void processAllTasksRetrieval(String response, RecyclerView recyclerView, CompleteTasksAdapter.OnItemClickListener clickListener) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                //create complete object
                CompleteTask completeTask = new CompleteTask(
                        object.getString("title"),
                        object.getString("due_date"),
                        object.getString("description"),
                        object.getString("state"),
                        object.getString("id"));
                //adding to list
                completeTaskList.add(completeTask);
            }
            //updating UI
            adapter = new CompleteTasksAdapter(clickListener, completeTaskList, getContext());
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void openActivity(CompleteTask completeTask) {
        Intent viewAcvtivity = new Intent(getContext(), ViewActivity.class);
        viewAcvtivity.putExtra(intentKey, completeTask.getId());
        getContext().startActivity(viewAcvtivity);
    }

    private void toggleStatus(CompleteTask completeTask, String status) {
        String url = mainUrl + "setStatus.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(getContext(), "Task status updated successfully", Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(getContext(), "Updated failed please check your connection", Toast.LENGTH_LONG).show()){
            //Request parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("task_id", completeTask.getId());
                params.put("status", status);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    private void toggleOn(CompleteTasksAdapter.CompleteTaskViewHolder completeTaskViewHolder, CompleteTask completeTask){
        completeTaskViewHolder.button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_toggle_on_white_36, 0, 0 , 0);
        completeTaskViewHolder.button.setChecked(false);
        completeTaskViewHolder.button.toggle();
        //Hit backend
    }

    private void toggleOff(CompleteTasksAdapter.CompleteTaskViewHolder completeTaskViewHolder, CompleteTask completeTask){
        completeTaskViewHolder.button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_toggle_off_white_36, 0, 0 , 0);
        completeTaskViewHolder.button.setChecked(true);
        completeTaskViewHolder.button.toggle();
        //Hit backend
    }

}
