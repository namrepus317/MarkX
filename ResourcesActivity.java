package com.example.christopher.markx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResourcesActivity extends AppCompatActivity {

    QuizletResource qResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        qResource = new QuizletResource(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
