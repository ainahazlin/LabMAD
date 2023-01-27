package com.example.labassignment3;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchStudentAct extends AppCompatActivity {

    TextView vname,vemail,vstate,vdate,vgen;
    EditText studID;
    Button search;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);

        //navigation initialization
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_close, R.string.nav_open);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_registration_activity:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_list_student:
                        intent = new Intent(getApplicationContext(), ViewStudents.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_search_student:
                        intent = new Intent(getApplicationContext(), SearchStudentAct.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_get_api:
                        intent = new Intent(getApplicationContext(), GetRESTAPI.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_first_act:
                        intent = new Intent(getApplicationContext(), FirstActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_user_interaction:
                        intent = new Intent(getApplicationContext(), UserInteraction_Register.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_get_external_image:
                        intent = new Intent(getApplicationContext(), SecondActivityCam.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        vname = findViewById(R.id.txtVwStudName2);
        vemail = findViewById(R.id.txtVwStudEmail);
        vdate = findViewById(R.id.txtVwDOB);
        vgen = findViewById(R.id.txtVwStudGender);
        vstate = findViewById(R.id.txtVwStudState);
        studID = findViewById(R.id.edtStudID);
        search = findViewById(R.id.btnSearch);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if the id text field is empty or not.
                if (TextUtils.isEmpty(studID.getText().toString())) {
                    Toast.makeText(SearchStudentAct.this, "Please enter course id", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling method to load data.
                getCourseDetails(studID.getText().toString());
            }
        });
    }

    private void getCourseDetails(String studId) {

        // url to post our data
        String url = "http://192.168.0.157/rest_api_search.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(SearchStudentAct.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // on below line passing our response to json object.
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are checking if the response is null or not.
                    jsonObject.getString("colName");
                    // if we get the data then we are setting it in our text views in below line.
                    vname.setText(jsonObject.getString("colName"));
                    vemail.setText(jsonObject.getString("colEmail"));
                    vdate.setText(jsonObject.getString("colDOB"));
                    vgen.setText(jsonObject.getString("colGender"));
                    vstate.setText(jsonObject.getString("colState"));
                    // on below line we are displaying
                    // a success toast message.
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(SearchStudentAct.this, "Fail to get student data" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key and value pair to our parameters.
                params.put("colID", studId);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}