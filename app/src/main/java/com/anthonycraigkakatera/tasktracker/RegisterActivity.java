package com.anthonycraigkakatera.tasktracker;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anthonycraigkakatera.tasktracker.model.StaffMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    //getting all fields
    private EditText firstName, lastName, email, postalAddress;
    private TextView registerButton;

    private StaffMember tempStaffMember;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        //dissapearing title bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //inflate
        inflateView();
        //submit form to API
        sendForm();
    }

    private void sendForm() {
        if(!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty()){
            if(!email.getText().toString().isEmpty()){
                postToAPI();
            }
        }
    }

    private void postToAPI() {
        String url = mainUrl + "register.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> processingResponse(response),
                error -> Toast.makeText(RegisterActivity.this, "Task creation failed please check your connection", Toast.LENGTH_LONG).show()){
            //Request parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", firstName.getText().toString());
                params.put("last_name", lastName.getText().toString());
                params.put("email_address", email.getText().toString());
                params.put("postal_address", postalAddress.getText().toString());
                return params;
            }
        };
        //Send the request to the API
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(stringRequest);
    }

    private void processingResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                //create complete object
                StaffMember staffMember = new StaffMember(
                        object.getString("name"),
                        object.getString("id"));
                //adding to list
                tempStaffMember = staffMember;
            }
            //confirm registration
            Toast.makeText(RegisterActivity.this, "Registration successful, auto logging in", Toast.LENGTH_LONG).show();

            //here we just open a new main activity and pass the login details
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.putExtra("name", tempStaffMember.getName());
            intent.putExtra("id", tempStaffMember.getId());
            RegisterActivity.this.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void inflateView() {
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        postalAddress = (EditText) findViewById(R.id.postalAddress);
        registerButton = (TextView) findViewById(R.id.registerButton);
    }

}
