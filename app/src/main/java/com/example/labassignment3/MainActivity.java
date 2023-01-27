package com.example.labassignment3;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.labassignment3.databinding.ActivityMainBinding;
import com.example.labassignment3.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    // creating variables for our edittext, button and dbhandler
    private EditText studname, studnum, studemail, studDOB;
    Spinner studstate;
    RadioButton rbMale, rbFemale;
    private FloatingActionButton addButton;
    private DBHandler dbHandler;
    AlertDialog.Builder builder;
    private DatePickerDialog datePicker;
    String url = "http://192.168.0.157/rest_api.php";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //navigation initialization
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_close, R.string.nav_open);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = findViewById(R.id.nav_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
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

        // initializing all our variables.
        studname = findViewById(R.id.stud_name);
        studemail = findViewById(R.id.stud_email);
        studnum = findViewById(R.id.stud_no);
        studstate = findViewById(R.id.spnState);
        studDOB = findViewById(R.id.edtBirthdate);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        addButton = findViewById(R.id.AddBttn);
        builder = new AlertDialog.Builder(MainActivity.this);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(MainActivity.this);


        studDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our edit text.
                                studDOB.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        // below line is to add on click listener for our add course button.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String name = studname.getText().toString();
                String num = studnum.getText().toString();
                String email = studemail.getText().toString();
                String state = studstate.getSelectedItem().toString();
                String dob = studDOB.getText().toString();

                // validating if the text fields are empty or not.
                if (name.isEmpty() && num.isEmpty() && email.isEmpty() && state.isEmpty() && dob.isEmpty() && rbFemale.isChecked()==false && rbMale.isChecked()==false) {
                    Toast.makeText(MainActivity.this, "Please enter fill the data", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (rbMale.isChecked()) {
                    dbHandler.addStudenttosqlite(num, name, email, state, rbMale.getText().toString(), dob);
                    insertintoremotedb(num, name, email, state, rbMale.getText().toString(), dob);
                } else {
                    dbHandler.addStudenttosqlite(num, name, email, state, rbFemale.getText().toString(), dob);
                    insertintoremotedb(num, name, email, state, rbFemale.getText().toString(), dob);
                }
                // after adding the data we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Student Details has been added.", Toast.LENGTH_SHORT).show();

            }

            public void insertintoremotedb(String num, String name, String email, String state, String gender, String dob) {
                //RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                builder.setTitle("Server Response");
                                builder.setMessage("Response :" + response);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        studnum.setText("");
                                        studname.setText("");
                                        studemail.setText("");
                                        studDOB.setText("");
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("colID", num);
                        params.put("colName", name);
                        params.put("colEmail", email);
                        params.put("colDOB", dob);
                        params.put("colGender", gender);
                        params.put("colState", state);
                        return params;
                    }
                };
                MySingleton.getInstance(MainActivity.this).addTorequestqueue(stringRequest);

            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}