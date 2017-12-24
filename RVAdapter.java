package com.example.christopher.markx;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 11/19/2017.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ClassViewHolder> {

    public static class ClassViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        ImageView iv;
        TextView className;
        TextView assignmentsDue;
        Button showAssignmentsButton;
        Button showResourcesButton;

        ClassViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            iv = (ImageView)itemView.findViewById(R.id.ivCardBackground);
            className = (TextView)itemView.findViewById(R.id.className);
            assignmentsDue = (TextView)itemView.findViewById(R.id.assignmentsDue);
            showAssignmentsButton = (Button)itemView.findViewById(R.id.showAssignmentsButton);
            showResourcesButton = (Button)itemView.findViewById(R.id.showResourcesButton);
        }

    }

    List<SchoolClass> schoolClasses;
    Context context;
    User user;
    FileIO fileIO;
    public RVAdapter(Context context, User user){
        this.schoolClasses = user.classes;
        this.context = context;
        this.user = user;
        fileIO = new FileIO(context, "AgendaData.desa");
    }

    @Override
    public int getItemCount(){
        return schoolClasses.size();
    }

    @Override
    public ClassViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_improvedcard_layout, viewGroup, false);
        ClassViewHolder avh = new ClassViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(final ClassViewHolder classViewHolder, final int i){

        schoolClasses.get(i).listIndex = i;
        classViewHolder.className.setText(schoolClasses.get(i).name);
        //SetCardImage
        Glide.with(context).load(schoolClasses.get(i).backgroundID).into(classViewHolder.iv);
        if(schoolClasses.get(i).getAssignments() != null) {
            classViewHolder.assignmentsDue.setText("Assignments Due: " + schoolClasses.get(i).getAssignments().size());
        }

        classViewHolder.showAssignmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, AssignmentsList.class);
                user.selectedClass = i;
                Log.d("TestPointNew", Integer.toString(i));
                fileIO.writeSaveData(user);
                context.startActivity(in);
            }
        });

        classViewHolder.showResourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, ResourcesActivity.class);
                user.selectedClass = i;
                fileIO.writeSaveData(user);
                context.startActivity(in);
            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

}
