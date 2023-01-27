package com.example.labassignment3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labassignment3.databinding.ActivityFirstBinding;
import com.google.android.material.navigation.NavigationView;

public class FirstActivity extends AppCompatActivity {

    TextView txtvwAge;
    EditText edtName, edtYear;
    Button btnClick;
    ImageView imagecapture;
    private ActivityFirstBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        txtvwAge = (TextView) findViewById(R.id.txtvwAge);
        edtName = (EditText) findViewById(R.id.edtTxtName);
        edtYear = (EditText) findViewById(R.id.edtYear);
        imagecapture = (ImageView) findViewById(R.id.Viewcapturedimage);


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
    }
    /*public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bp = (Bitmap) data.getExtras().get("image");
        imagecapture.setImageBitmap(bp);
    }*/

    public void fnGreet(View vw) {
        String strName = edtName.getText().toString();
        txtvwAge.setText("Hello! Welcome " + strName + ".\n Your Age is " + (2023 - (Integer.parseInt(edtYear.getText().toString()))) + " years old");
    }

    public void fnThreadActivity(View vw) {
        Intent intent = new Intent(this, ThreadedActivity.class);
        String strMsg = ((EditText) findViewById(R.id.edtTxtName)).getText().toString();
        intent.putExtra("varStr1", strMsg);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "This happen when app is started", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "This happen when app is about to restarting", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "This happen when app is about to resuming back", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "This happen when app is about to pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "This happen when app is about to stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "This happen when app is about to destroy", Toast.LENGTH_SHORT).show();
    }
}