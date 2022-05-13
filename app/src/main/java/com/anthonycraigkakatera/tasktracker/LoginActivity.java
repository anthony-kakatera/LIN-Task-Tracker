package com.anthonycraigkakatera.tasktracker;

import static com.anthonycraigkakatera.tasktracker.MainActivity.mainUrl;
import static com.anthonycraigkakatera.tasktracker.MainActivity.yourLoginDetails;
import static com.anthonycraigkakatera.tasktracker.ViewActivity.intentKey;

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
import com.anthonycraigkakatera.tasktracker.adapters.CommentsAdapter;
import com.anthonycraigkakatera.tasktracker.model.Comment;
import com.anthonycraigkakatera.tasktracker.model.StaffMember;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    //username and password for login
    private EditText username, password;
    private TextView loginButton, signUp;
    //an object to hold the login details upon success
    private StaffMember tempStaffMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        //inflate view
        inflateViews();
        //submit to api
        login();
        //register
        register();
    }

    private void login() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCredentialsToAPI();
            }
        });



    }

    private void sendCredentialsToAPI() {
        String url = mainUrl + "login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> processResponse(response),
                error -> Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show()){
            //Request parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        //Send the request to the API
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(stringRequest);
    }

    private void processResponse(String response) {
        if(response.length() > 3){
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
                //here we just open a new main activity and pass the login details
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("name", tempStaffMember.getName());
                intent.putExtra("id", tempStaffMember.getId());
                LoginActivity.this.startActivity(intent);
                //closing this activity
                LoginActivity.this.finish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(LoginActivity.this, "Please try again your login credentials were rejected", Toast.LENGTH_LONG).show();
        }
    }

    private void register() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here we just open a new register activity and pass the login details
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                //closing this activity
                LoginActivity.this.finish();
            }
        });
    }

    private void inflateViews() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (TextView) findViewById(R.id.loginButton);
        signUp = (TextView) findViewById(R.id.signUp);
    }


}
