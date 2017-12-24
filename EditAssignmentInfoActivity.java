package com.example.christopher.markx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditAssignmentInfoActivity extends AppCompatActivity {

    public int day, month, year;
    public int nDay, nMonth, nYear;
    public Calendar currentDate;
    //public Calendar d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        final EditText inputName = (EditText) findViewById(R.id.nameinput);
        final EditText dueDateText = (EditText) findViewById(R.id.dueDateText);
        final EditText inputNotes = (EditText) findViewById(R.id.assignmentNotesInput);

        Toolbar toolbar = (Toolbar)findViewById(R.id.appBarEditAssignment);
        setSupportActionBar(toolbar);
        setTitle("Edit Assignment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        currentDate = Calendar.getInstance();
        //d = Calendar.getInstance();
        day = currentDate.get(Calendar.DAY_OF_MONTH);
        month = currentDate.get(Calendar.MONTH);
        year = currentDate.get(Calendar.YEAR);
        dueDateText.setText((month + 1) + "/" + day + "/" + year);

        if(getIntent().hasExtra("AssignmentData")){
            Assignment a = getIntent().getParcelableExtra("AssignmentData");
            inputName.setText(a.name);
            dueDateText.setText(a.dueDate);
            inputNotes.setText(a.notes);
        }

        Button bb = (Button) findViewById(R.id.bb);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assignment a = new Assignment(inputName.getText().toString(), dueDateText.getText().toString(), inputNotes.getText().toString(), getApplicationContext() );
                //d.set(nYear, nMonth, nDay);
                //a.date = d;
                Intent i = new Intent(getApplicationContext(), AssignmentsList.class);

                if(getIntent().hasExtra("AssignmentData")){
                    i.putExtra("UpdatedAssignmentData", (Parcelable) a);
                    i.putExtra("AssignmentPos", getIntent().getIntExtra("AssignmentPos", 99));
                }
                else{
                    i.putExtra("assignmentData", (Parcelable) a);
                }
                Log.d("TestPointBravo", "Zoidberg");
                startActivity(i);
                finish();

            }
        });

        dueDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditAssignmentInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dueDateText.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                        nYear = year;
                        nMonth = month;
                        nDay = dayOfMonth;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }




    @Override
    protected void onStop() {
        super.onStop();
    }


}
