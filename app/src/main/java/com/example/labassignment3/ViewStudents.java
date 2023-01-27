package com.example.labassignment3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ViewStudents extends AppCompatActivity {

    // creating variables for our array list,
    // dbhandler, adapter and recycler view.
    private ArrayList<Student> studentsModalArrayList;
    private DBHandler dbHandler;
    private StudentsRVAdapter studentsRVAdapter;
    private RecyclerView studentsRV;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

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

        // initializing our all variables.
        studentsModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(ViewStudents.this);

        // getting our course array
        // list from db handler class.
        studentsModalArrayList = dbHandler.readStudents();

        // on below line passing our array lost to our adapter class.
        studentsRVAdapter = new StudentsRVAdapter(studentsModalArrayList, ViewStudents.this);
        studentsRV = findViewById(R.id.idRVCourses);

        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewStudents.this, RecyclerView.VERTICAL, false);
        studentsRV.setLayoutManager(linearLayoutManager);

        // setting our adapter to recycler view.
        studentsRV.setAdapter(studentsRVAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(studentsRV);

        studentsRVAdapter.notifyDataSetChanged();
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewStudents.this);
            builder.setTitle("Delete Student Details");
            builder.setMessage("Are You Sure?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int position = viewHolder.getAdapterPosition();
                    studentsModalArrayList.remove(position);
                    studentsRVAdapter.notifyItemRemoved(position);
                    //dbHandler.deleteStudents("strStudNo");
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    studentsRVAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            builder.show();
        }

    };


    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
