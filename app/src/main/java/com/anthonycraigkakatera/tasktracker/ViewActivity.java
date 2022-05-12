package com.anthonycraigkakatera.tasktracker;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;
import static com.anthonycraigkakatera.tasktracker.MainActivity.yourLoginDetails;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.anthonycraigkakatera.tasktracker.adapters.CommentsAdapter;
import com.anthonycraigkakatera.tasktracker.adapters.CompleteTasksAdapter;
import com.anthonycraigkakatera.tasktracker.model.Comment;
import com.anthonycraigkakatera.tasktracker.model.CompleteTask;
import com.anthonycraigkakatera.tasktracker.model.GeneralTask;
import com.anthonycraigkakatera.tasktracker.model.StaffMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewActivity extends AppCompatActivity {
    private int mColumnCount = 1;
    private static final String ARG_COLUMN_COUNT = "column-count";

    private TextView title, description, dueDate, assigned, status, staffComments;
    private List<StaffMember> staffMemberList = new ArrayList<>();
    private String taskID;
    private String tempAssignedStaff = "";
    public static String intentKey = "taskID";
    private GeneralTask newGeneralTask;
    private List<Comment> commentList = new ArrayList<>();
    private String comments = "";

    //adapter for comments
    public CommentsAdapter.OnItemClickListener clickListener;
    public CommentsAdapter adapter;

    public EditText comment;
    public TextView submitTaskButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_layout);
        //inflate
        inflateViews();
        //get intent values
        taskID = (String) getIntent().getStringExtra(intentKey);
        //download and display content
        downloadTaskContent();
        downloadAssignedStaffMembers();
        //recyclerview
        RecyclerView recycler = findViewById(R.id.staffMemberCommentsRecycler);
        //Recycler for comments
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
        //submit
        sendComment();
    }

    private void sendComment() {
        submitTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!comment.getText().toString().isEmpty())
                    submitComment();
            }
        });
    }

    private void setRecyclerAdapter(RecyclerView recyclerView, RecyclerView recycler) {
        clickListener = new CommentsAdapter.OnItemClickListener() {
            @NonNull
            @Override
            public String toString() {
                return super.toString();
            }
        };
        //download comments
        downloadComments(recyclerView);
    }

    private void downloadComments(RecyclerView recyclerView) {
        String url = mainUrl + "getComments.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> processingResponse(response, recyclerView),
                error -> Toast.makeText(ViewActivity.this, "Task creation failed please check your connection", Toast.LENGTH_LONG).show()){
            //Request parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("task_id", taskID);
                return params;
            }
        };
        //Send the request to the API
        RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
        queue.add(stringRequest);
    }

    private void processingResponse(String response, RecyclerView recyclerView) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                //create complete object
                Comment comment = new Comment(
                        object.getString("name"),
                        object.getString("date"),
                        object.getString("comment"));
                //adding to list
                commentList.add(comment);
            }
            //update UI
            adapter = new CommentsAdapter(clickListener, commentList, ViewActivity.this);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void downloadAssignedStaffMembers() {
        String url = mainUrl + "getAssignedStaffMembers.php";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //create complete object
                        StaffMember staffMember = new StaffMember(
                                object.getString("name"),
                                object.getString("id"));
                        //adding to list
                        staffMemberList.add(staffMember);
                    }
                    //updating UI
                    for(StaffMember staffMember : staffMemberList){
                        tempAssignedStaff = tempAssignedStaff + staffMember + "\n";
                    }
                    //set the text
                    assigned.setText(tempAssignedStaff);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ViewActivity.this);
        requestQueue.add(request);
    }

    private void downloadTaskContent() {
        String url = mainUrl + "getSpecificTasks.php";
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //weatherData.setText("Response is :- ");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject object = (JSONObject) jsonArray.get(i);
                        //create complete object
                        GeneralTask generalTask = new GeneralTask(
                                object.getString("title"),
                                object.getString("dueDate"),
                                object.getString("description"),
                                object.getString("status"),
                                object.getString("id"));
                        //adding to list
                        newGeneralTask = generalTask;
                    }
                    //updating UI
                    title.setText(newGeneralTask.getTitle());
                    description.setText(newGeneralTask.getDescription());
                    dueDate.setText(newGeneralTask.getDueDate());
                    status.setText(newGeneralTask.getStatus());
                    //now download assigned staff members
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(ViewActivity.this);
        requestQueue.add(request);
    }

    private void submitComment(){
        String url = mainUrl + "setComment.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(ViewActivity.this, "Comment submitted successfully", Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(ViewActivity.this, "Task creation failed please check your connection", Toast.LENGTH_LONG).show()){
            //Request parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("task_id", taskID);
                params.put("comment", comment.getText().toString());
                params.put("user_id", yourLoginDetails.getId());
                return params;
            }
        };
        //Send the request to the API
        RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
        queue.add(stringRequest);
    }

    private void inflateViews() {
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        dueDate = (TextView) findViewById(R.id.dueDate);
        assigned = (TextView) findViewById(R.id.assigned);
        status = (TextView) findViewById(R.id.status);
        staffComments = (TextView) findViewById(R.id.comments);
        submitTaskButton = (TextView) findViewById(R.id.submitTaskButton);

        comment = (EditText) findViewById(R.id.comment);
    }
}
