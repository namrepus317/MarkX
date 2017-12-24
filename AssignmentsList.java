package com.example.christopher.markx;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class AssignmentsList extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Assignment> assignments;
    private String fileName = "AgendaData.desa";
    private FileIO fileIO;
    private User user;
    private int selectedClass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments_list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.assignmentsListTB);
        setSupportActionBar(toolbar);

        fileIO = new FileIO(getApplicationContext(), fileName);
        user = (User) fileIO.getSaveData();
        selectedClass = user.selectedClass;
        assignments = user.classes.get(selectedClass).getAssignments();
        if(getIntent().hasExtra("UpdatedAssignmentData")){
            assignments.set(getIntent().getIntExtra("AssignmentPos", 99),(Assignment)getIntent().getParcelableExtra("UpdatedAssignmentData"));
        }

        final ImageView background = (ImageView)findViewById(R.id.assignmentsListBackground);
        toolbar.setBackgroundColor(Color.parseColor(user.classes.get(selectedClass).colorCode));
        Glide.with(this).load(user.classes.get(selectedClass).backgroundID).into(background);
        setupList();

        FloatingActionButton addAssignmentButton = (FloatingActionButton)findViewById(R.id.addAssignmentFAB);
        addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditAssignmentInfoActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().hasExtra("assignmentData")){
            assignments.add((Assignment)getIntent().getParcelableExtra("assignmentData"));
            getIntent().removeExtra("assignmentData");
            fileIO.writeSaveData(user);
            //Toast.makeText(this, Boolean.toString((assignments.get(9).date) == null), Toast.LENGTH_SHORT).show();
            setupList();
        }
        fileIO.writeSaveData(user);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow_assignments_list ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.overflowSortAssignments:
                user.classes.get(selectedClass).sortAssignmentsByDate();
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        fileIO.writeSaveData(user);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        fileIO.writeSaveData(user);
    }

    private void setupList(){
        if(assignments.size() > 0 && mAdapter == null){
            mRecyclerView = (RecyclerView)findViewById(R.id.assignmentsRV);

            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new AssignmentRVAdapter(getApplicationContext(), assignments);
            mRecyclerView.setAdapter(mAdapter);
            Log.d("TestPointNewNew", Integer.toString(selectedClass));
        }
    }

}
