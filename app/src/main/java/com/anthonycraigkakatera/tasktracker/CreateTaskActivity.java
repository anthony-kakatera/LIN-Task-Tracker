package com.anthonycraigkakatera.tasktracker;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;
import static com.anthonycraigkakatera.tasktracker.MainActivity.yourLoginDetails;

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
    private EditText title, description;
    private RadioButton completed, incomplete;
    private TextView createTaskButton, dueDate, back;

    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task_layout);
        //inflating views
        inflateViews();
        //activate dueDate
        activateDueDateFeature();
        //prep submittion
        sendCreateData();
        //return home
        returnHome();
    }

    private void returnHome() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                params.put("staff_id", yourLoginDetails.getId());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(CreateTaskActivity.this);
        queue.add(stringRequest);
    }

    private String ifChecked() {
        if(incomplete.isChecked())
        {
            return "1";
        }else if(completed.isChecked())
        {
            // is checked
            return "2";
        }
        return "1";
    }

    private void activateDueDateFeature() {
        //dissable keyboard

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
        /*DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                //update dueDate UI
                updateDueDate();
            }
        };*/
        String date_s = " 2011-01-18 00:00:00.0";
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");

        final Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog  StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dueDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        //display the popup
        StartTime.show();
    }

    private void updateDueDate(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ENGLISH);
        dueDate.setText(dateFormat.format(calendar.getTime()));
    }

    private void inflateViews() {
        title = (EditText) findViewById(R.id.title);
        dueDate = (TextView) findViewById(R.id.date);
        description = (EditText) findViewById(R.id.description);

        completed = (RadioButton) findViewById(R.id.completed);
        incomplete = (RadioButton) findViewById(R.id.incomplete);

        createTaskButton = (TextView) findViewById(R.id.createTaskButton);
        back = (TextView) findViewById(R.id.back);
    }
}
