package com.anthonycraigkakatera.tasktracker.tabs;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;
import static com.anthonycraigkakatera.tasktracker.ViewActivity.intentKey;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anthonycraigkakatera.tasktracker.AssignTasksActivity;
import com.anthonycraigkakatera.tasktracker.CreateTaskActivity;
import com.anthonycraigkakatera.tasktracker.R;
import com.anthonycraigkakatera.tasktracker.ViewActivity;
import com.anthonycraigkakatera.tasktracker.adapters.CompleteTasksAdapter;
import com.anthonycraigkakatera.tasktracker.model.CompleteTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeTab extends Fragment {
    private int mColumnCount = 1;
    private static final String ARG_COLUMN_COUNT = "column-count";
    //getting buttons for the home screen
    private LinearLayout incompleteTaskButton, addATaskButton, assignATaskButton, completedTaskButton;
    //getting the counter displays for incomplete and complete tasks
    private TextView incompleteTaskCount, completedTasksCount;
    //counts for all tasks
    String incompleteTasks, completeTasks;
    //use this to navigate tabs
    private ViewPager viewPager;

    public void loadViewPager(ViewPager mainViewPager){
        viewPager = mainViewPager;
    }


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
        View view = inflater.inflate(R.layout.fragment_home_layout, container, false);
        //inflating all views
        inflateViews(view);
        //activating all buttons by adding a onClick listener
        activateMenuButtons();
        //downloading total counts for all incomplete and complete tasks
        downloadCompletedCounts();
        downloadIncompletedCounts();
        //returning the view
        return view;
    }

    private void inflateViews(View view) {
        //inflating the buttons
        incompleteTaskButton = (LinearLayout) view.findViewById(R.id.incompleteTaskButton);
        addATaskButton = (LinearLayout) view.findViewById(R.id.addATaskButton);
        assignATaskButton = (LinearLayout) view.findViewById(R.id.assignATaskButton);
        completedTaskButton = (LinearLayout) view.findViewById(R.id.completedTaskButton);
        //inflating textviews
        incompleteTaskCount = (TextView) view.findViewById(R.id.incompleteTaskCount);
        completedTasksCount = (TextView) view.findViewById(R.id.completedTasksCount);
    }

    private void activateMenuButtons() {
        incompleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to incomplete tasks tab
                viewPager.setCurrentItem(1);
            }
        });
        addATaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open new addTask activity
                openCreateTaskActivity();
            }
        });
        assignATaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open new assign task activity
                openAssignActivity();
            }
        });
        completedTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to complete tasks tab
                viewPager.setCurrentItem(2);
            }
        });
    }

    private void downloadCompletedCounts() {
        String url = mainUrl + "getCompleteTasksCount.php";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //create complete object
                        completeTasks = object.getString("count");
                    }
                    //updating UI
                    completedTasksCount.setText(completeTasks);
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

    private void downloadIncompletedCounts() {
        String url = mainUrl + "getIncompleteTasksCount.php";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //create complete object
                        incompleteTasks = object.getString("count");
                    }
                    //updating UI
                    incompleteTaskCount.setText(incompleteTasks);
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

    private void openAssignActivity() {
        Intent intent = new Intent(getContext(), AssignTasksActivity.class);
        getContext().startActivity(intent);
    }

    private void openCreateTaskActivity() {
        Intent intent = new Intent(getContext(), CreateTaskActivity.class);
        getContext().startActivity(intent);
    }
}
