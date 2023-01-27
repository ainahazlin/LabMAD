package com.example.labassignment3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentsRVAdapter extends RecyclerView.Adapter<StudentsRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<Student> studentsModalArrayList;
    private Context context;

    // constructor
    public StudentsRVAdapter(ArrayList<Student> studentsModalArrayList, Context context) {
        this.studentsModalArrayList = studentsModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        Student modal = studentsModalArrayList.get(position);
        holder.strFullname.setText(modal.getStrFullname());
        holder.strStudNo.setText(modal.getStrStudNo());
        holder.strEmail.setText(modal.getStrEmail());
        holder.strGender.setText(modal.getStrGender());
        holder.strBirthdate.setText(modal.getStrBirthdate());
        holder.strState.setText(modal.getStrState());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return studentsModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView strFullname, strStudNo, strEmail, strGender, strBirthdate, strState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            strFullname = itemView.findViewById(R.id.idTVFullName);
            strStudNo = itemView.findViewById(R.id.idTVStudentNum);
            strEmail = itemView.findViewById(R.id.idTVEmail);
            strGender = itemView.findViewById(R.id.idTVGender);
            strBirthdate = itemView.findViewById(R.id.idTVBirth);
            strState = itemView.findViewById(R.id.idTVState);
        }
    }
}

