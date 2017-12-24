package com.example.christopher.markx;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AddClassDialogFragment.AddClassDialogListener {

    private String fileName = "AgendaData.desa";
    private User user;
    private FileIO fileIO;
    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //Obtaining the SchoolClass Data File
        fileIO = new FileIO(getApplicationContext(), fileName);
        user = (User) fileIO.getSaveData();
        //If no user currently exists
        if (user == null) {
            user = new User();
            fileIO.writeSaveData(user);
        }

        //Set Adapter
        setupList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupList();
        //adapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {
        super.onStop();
        fileIO.writeSaveData(user);
    }

    private void setupList(){
        if(user.classes.size() != 0) {
            RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            rv.setLayoutManager(llm);

            adapter = new RVAdapter(this, user);
            rv.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow_menu ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.overflowMainAddClass:
                DialogFragment addClassDialog = new AddClassDialogFragment();
                addClassDialog.show(getSupportFragmentManager(), "AddClassDialogFragment");
                return true;
        }
        return true;
    }

    @Override
    public void onDialogPositiveClick(SchoolClass c){
        user.addClass(c);
        fileIO.writeSaveData(user);
        setupList();

    }

}
