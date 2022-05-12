package com.anthonycraigkakatera.tasktracker;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.anthonycraigkakatera.tasktracker.adapters.AssignTaskAdapter;
import com.anthonycraigkakatera.tasktracker.adapters.CompleteTasksAdapter;
import com.anthonycraigkakatera.tasktracker.adapters.StaffMemberAdapter;
import com.anthonycraigkakatera.tasktracker.model.CompleteTask;
import com.anthonycraigkakatera.tasktracker.model.GeneralTask;
import com.anthonycraigkakatera.tasktracker.model.StaffMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignTasksActivity extends AppCompatActivity {
    public AssignTaskAdapter.OnItemClickListener clickListener;
    public StaffMemberAdapter.OnItemClickListener clickListener2;
    public AssignTaskAdapter adapter;
    public StaffMemberAdapter adapter2;
    private List<GeneralTask> generalTaskList;
    private List<StaffMember> staffMemberList;
    private int mColumnCount = 1;
    public GeneralTask heldGeneralTask;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assignment_layout);
        //inflating title to display an active task
        title = (TextView) findViewById(R.id.taskName);
        //dissapearing title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //load genral tasks recycler
        RecyclerView tasksRecyclerView = findViewById(R.id.completedTasksRecyclerView);
        RecyclerView staffRecyclerView = findViewById(R.id.staffMemberRecycler);
        // Set the adapter
        if (tasksRecyclerView instanceof RecyclerView) {
            Context context = tasksRecyclerView.getContext();
            RecyclerView recyclerView = (RecyclerView) tasksRecyclerView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
            }

            if (staffRecyclerView instanceof RecyclerView) {
                Context context2 = staffRecyclerView.getContext();
                RecyclerView recyclerView2 = (RecyclerView) staffRecyclerView;
                if (mColumnCount <= 1) {
                    recyclerView2.setLayoutManager(new LinearLayoutManager(context2));
                } else {
                    recyclerView2.setLayoutManager(new GridLayoutManager(context2, mColumnCount));
                }
                //setting the recycler view and adapter
                setRecyclerAdapter(tasksRecyclerView, staffRecyclerView);
            }
        }else{

        }
    }

    public void setRecyclerAdapter(RecyclerView tasksRecyclerView, RecyclerView staffRecyclerView){
        clickListener = new AssignTaskAdapter.OnItemClickListener() {
            @Override
            public void onClick(AssignTaskAdapter.AssignTaskViewHolder assignTaskViewHolder, GeneralTask generalTask) {
                Toast.makeText(AssignTasksActivity.this, "Please a staff member you wish to assign this task", Toast.LENGTH_LONG).show();
                //now show selected task name
                title.setText(generalTask.getTitle());
                //now hold this object for assigning
                heldGeneralTask = generalTask;
            }
        };
        clickListener2 = new StaffMemberAdapter.OnItemClickListener() {
            @Override
            public void onClick(StaffMemberAdapter.StaffMemberViewHolder staffMemberViewHolder, StaffMember staffMember) {
                //bring popup here then assign
                if(heldGeneralTask != null){
                    assignmentPopup(staffMember);
                }else{
                    Toast.makeText(AssignTasksActivity.this, "Please select a task then select an employee", Toast.LENGTH_LONG).show();
                }
            }
        };

        //downloading json data via volley api and then update interface upon completion
        downloadTasksContent(tasksRecyclerView, clickListener);
        downloadStaffContent(staffRecyclerView, clickListener2);
    }

    private void assignmentPopup(StaffMember staffMember){
        new AlertDialog.Builder(AssignTasksActivity.this)
                .setTitle("Assignment Confirmation")
                .setMessage(
                        "Do you wish to assign"+ heldGeneralTask.getTitle() +"to " + staffMember.getName()
                )
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Post the request
                        sendAssignmentPostToAPI(staffMember);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void sendAssignmentPostToAPI(StaffMember staffMember) {
        String url = mainUrl + "assignTask.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(AssignTasksActivity.this, "Task assigned successfully", Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(AssignTasksActivity.this, "Task assignment failed", Toast.LENGTH_LONG).show()){
            //Request parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("task_id", heldGeneralTask.getId());
                params.put("staff_id", staffMember.getId());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(AssignTasksActivity.this);
        queue.add(stringRequest);
    }

    private void downloadTasksContent(RecyclerView recyclerView, AssignTaskAdapter.OnItemClickListener clickListener) {
        String url = mainUrl + "getAllTasks.php";
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
                        generalTaskList.add(generalTask);
                    }
                    //updating UI
                    adapter = new AssignTaskAdapter(clickListener, generalTaskList, AssignTasksActivity.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(AssignTasksActivity.this);
        requestQueue.add(request);
    }

    private void downloadStaffContent(RecyclerView recyclerView, StaffMemberAdapter.OnItemClickListener clickListener) {
        String url = mainUrl + "getAllStaff.php";
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
                    adapter2 = new StaffMemberAdapter(clickListener, staffMemberList, AssignTasksActivity.this);
                    recyclerView.setAdapter(adapter2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(AssignTasksActivity.this);
        requestQueue.add(request);
    }
}
