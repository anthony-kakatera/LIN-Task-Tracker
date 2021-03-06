package com.anthonycraigkakatera.tasktracker;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    private EditText firstName, lastName, email, postalAddress, password;
    private TextView registerButton, back;

    private StaffMember tempStaffMember;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        //inflate
        inflateView();
        //submit form to API
        sendForm();
        //back button
        backToLogin();
    }

    private void backToLogin() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                //closing this activity
                RegisterActivity.this.finish();
            }
        });
    }

    private void sendForm() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !postalAddress.getText().toString().isEmpty()){
                    if(!email.getText().toString().isEmpty()){
                        postToAPI();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Please complete the form by filling in all fields", Toast.LENGTH_LONG).show();
                }
            }
        });
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
                params.put("password", password.getText().toString());
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
                        object.getString("first_name") + " " + object.getString("last_name"),
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
        password = (EditText) findViewById(R.id.password);
        registerButton = (TextView) findViewById(R.id.registerButton);
        back = (TextView) findViewById(R.id.back);
    }

}
