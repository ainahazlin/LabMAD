package com.example.labassignment3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.labassignment3.databinding.ActivityUserInteractionRegisterBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class UserInteraction_Register extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    DatePickerDialog datePicker;
    ActivityUserInteractionRegisterBinding binding;
    final static public String USER_KEY = "objUser";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInteractionRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.edtBirthdate.setOnFocusChangeListener((v, hasFocus) -> fnInvokeDatePicker());
        binding.edtBirthdate.setOnClickListener(v -> fnInvokeDatePicker());
        binding.edtFullName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Toast.makeText(getApplicationContext(), binding.edtFullName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.edtPwd.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Toast.makeText(getApplicationContext(), binding.edtPwd.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.edtEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Toast.makeText(getApplicationContext(), binding.edtEmail.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.edtAddress.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                Toast.makeText(getApplicationContext(), binding.edtAddress.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.edtFullName.addTextChangedListener(RegTextWatcher);
        binding.edtPwd.addTextChangedListener(RegTextWatcher);
        binding.edtEmail.addTextChangedListener(RegTextWatcher);
        binding.edtBirthdate.addTextChangedListener(RegTextWatcher);
        binding.edtAddress.addTextChangedListener(RegTextWatcher);
        binding.fabAddUser.setOnClickListener(this:: fnAddUser);
    }

    private void fnInvokeDatePicker()
    {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        datePicker = new DatePickerDialog(UserInteraction_Register.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.edtBirthdate.setText(dayOfMonth + "/" + (month + 1) + "/" + (year));
            }
        },year, month, day);
        datePicker.show();
    }

    private TextWatcher RegTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = binding.edtFullName.getText().toString().trim();
            String pwdInput = binding.edtPwd.getText().toString().trim();
            String emailInput = binding.edtEmail.getText().toString().trim();
            String birthInput = binding.edtBirthdate.getText().toString().trim();
            String addressInput = binding.edtAddress.getText().toString().trim();

            binding.fabAddUser.setEnabled(!nameInput.isEmpty() && !pwdInput.isEmpty() &&
                    !emailInput.isEmpty() && !birthInput.isEmpty() && !addressInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void fnAddUser(View view)
    {
        String strFullName = binding.edtFullName.getText().toString();
        String strPwd = binding.edtPwd.getText().toString();
        String strEmail = binding.edtEmail.getText().toString();
        String strBirth = binding.edtBirthdate.getText().toString();
        String strAddress = binding.edtAddress.getText().toString();
        String strGender = "";

        if(binding.rbMale.isChecked())
            strGender = binding.rbMale.getText().toString();
        else if(binding.rbFemale.isChecked())
            strGender = binding.rbFemale.getText().toString();

        String finalStrGender = strGender;

        User user = new User(strFullName, strPwd, strAddress, strEmail, strBirth, finalStrGender) ;

        Intent intent = new Intent(this, UserInteraction.class);
        intent.putExtra(USER_KEY, user.fullname + "\n\n" + user.pwd + "\n\n" + user.email + "\n\n"
                + user.birthdate + "\n\n" + user.address + "\n\n" + user.gender);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}