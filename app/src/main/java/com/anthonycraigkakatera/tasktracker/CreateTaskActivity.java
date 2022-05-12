package com.anthonycraigkakatera.tasktracker;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateTaskActivity extends AppCompatActivity {
    private EditText title, dueDate, description;
    private RadioButton completed, incomplete;
    private TextView createTaskButton;

    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task_layout);
        //dissapearing title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //inflating views
        inflateViews();
        //activate dueDate
        activateDueDateFeature();
        //prep submittion
        sendCreateData();
    }

    private void sendCreateData() {
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sending to api
                postToAPI();
            }
        });
    }

    private void postToAPI() {
        String url = mainUrl + "createTask.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(CreateTaskActivity.this, "Task created successfully", Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(CreateTaskActivity.this, "Task creation failed please check your connection", Toast.LENGTH_LONG).show()){
            //Request parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title.getText().toString());
                params.put("description", description.getText().toString());
                params.put("due_date", dueDate.getText().toString());
                params.put("status", ifChecked());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(CreateTaskActivity.this);
        queue.add(stringRequest);
    }

    private String ifChecked() {
        if(incomplete.isChecked())
        {
            return "incomplete";
        }else if(completed.isChecked())
        {
            // is checked
            return "completed";
        }
        return "incomplete";
    }

    private void activateDueDateFeature() {
        //dissable keyboard
        dueDate.setKeyListener(null);
        //activate date picker
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling date picker here
                popupDatePicker();
            }
        });
    }

    public void popupDatePicker(){
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                //update dueDate UI
                updateDueDate();
            }
        };
    }

    private void updateDueDate(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        dueDate.setText(dateFormat.format(calendar.getTime()));
    }

    private void inflateViews() {
        title = (EditText) findViewById(R.id.title);
        dueDate = (EditText) findViewById(R.id.date);
        description = (EditText) findViewById(R.id.description);

        completed = (RadioButton) findViewById(R.id.completed);
        incomplete = (RadioButton) findViewById(R.id.incomplete);

        createTaskButton = (TextView) findViewById(R.id.createTaskButton);
    }
}
