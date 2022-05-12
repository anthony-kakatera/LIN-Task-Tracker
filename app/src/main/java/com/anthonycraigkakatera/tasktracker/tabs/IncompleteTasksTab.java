package com.anthonycraigkakatera.tasktracker.tabs;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;
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
import com.anthonycraigkakatera.tasktracker.adapters.IncompleteTasksAdapter;
import com.anthonycraigkakatera.tasktracker.model.IncompleteTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncompleteTasksTab extends Fragment {
    private int mColumnCount = 1;
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static  IncompleteTasksAdapter.OnItemClickListener clickListener;
    public static  IncompleteTasksAdapter adapter;
    private List<IncompleteTask> incompleteTaskList;

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
        View view = inflater.inflate(R.layout.fragment_incomplete_tasks_layout, container, false);
        //getting the recycler view and inflating it
        RecyclerView recycler = view.findViewById(R.id.incompleteTasksRecyclerView);
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
        return view;
    }

    public void setRecyclerAdapter(RecyclerView recyclerView, View view){
        clickListener = new IncompleteTasksAdapter.OnItemClickListener() {
            @Override
            public void onClick(IncompleteTasksAdapter.InompleteTaskViewHolder inompleteTaskViewHolder, IncompleteTask incompleteTask) {
                //called when the whole view is clicked
                //here we open a new intent
                openActivity(incompleteTask);
            }

            @Override
            public void onClickToggle(IncompleteTasksAdapter.InompleteTaskViewHolder inompleteTaskViewHolder, IncompleteTask incompleteTask) {
                if(inompleteTaskViewHolder.button.isChecked()){
                    //toggle on
                    toggleOn(inompleteTaskViewHolder, incompleteTask);
                    //adjusting the status at the backend
                    toggleStatus(incompleteTask, "complete");
                }else{
                    //toggle off
                    toggleOff(inompleteTaskViewHolder, incompleteTask);
                    //adjusting the status at the backend
                    toggleStatus(incompleteTask, "incomplete");
                }
            }
        };
        //downloading json data via volley api and then update interface upon completion
        downloadContent(recyclerView, clickListener);
    }

    private void openActivity(IncompleteTask incompleteTask) {
        Intent viewAcvtivity = new Intent(getContext(), ViewActivity.class);
        viewAcvtivity.putExtra(intentKey, incompleteTask.getId());
        getContext().startActivity(viewAcvtivity);
    }

    private void toggleStatus(IncompleteTask incompleteTask, String status) {
        String url = mainUrl + "setStatus.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(getContext(), "Task status updated successfully", Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(getContext(), "Updated failed please check your connection", Toast.LENGTH_LONG).show()){
            //Request parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("task_id", incompleteTask.getId());
                params.put("status", status);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    private void downloadContent(RecyclerView recyclerView, IncompleteTasksAdapter.OnItemClickListener clickListener) {
        String url = mainUrl + "getIncompleteTasks.php";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //create incomplete object
                        IncompleteTask incompleteTask = new IncompleteTask(
                                object.getString("title"),
                                object.getString("dueDate"),
                                object.getString("description"),
                                object.getString("status"),
                                object.getString("id"));
                        //adding to list
                        incompleteTaskList.add(incompleteTask);
                    }
                    //updating UI
                    adapter = new IncompleteTasksAdapter(clickListener, incompleteTaskList, getContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void toggleOn(IncompleteTasksAdapter.InompleteTaskViewHolder inompleteTaskViewHolder, IncompleteTask incompleteTask){
        inompleteTaskViewHolder.button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_toggle_on_white_36, 0, 0 , 0);
        inompleteTaskViewHolder.button.setChecked(false);
        inompleteTaskViewHolder.button.toggle();
        //Hit backend
    }

    private void toggleOff(IncompleteTasksAdapter.InompleteTaskViewHolder inompleteTaskViewHolder, IncompleteTask incompleteTask){
        inompleteTaskViewHolder.button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.baseline_toggle_off_white_36, 0, 0 , 0);
        inompleteTaskViewHolder.button.setChecked(true);
        inompleteTaskViewHolder.button.toggle();
        //Hit backend
    }
}
