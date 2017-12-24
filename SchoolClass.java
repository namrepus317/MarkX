package com.example.christopher.markx;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Christopher on 10/21/2017.
 */

public class SchoolClass implements Parcelable, Serializable {

    private ArrayList<Assignment> assignments;
    String name;
    String colorCode;
    int listIndex;
    int backgroundID;

    public SchoolClass(Parcel in) {
        in.readTypedList(assignments, Assignment.CREATOR);
        in.readString();
        in.readString();
        in.readInt();
        in.readInt();
    }

    public SchoolClass(ArrayList<Assignment> assignments, String name, String colorCode){
        this.assignments = assignments;
        this.name = name;
        this.colorCode = translateColor(colorCode);
    }

    public SchoolClass(String name){
        this.assignments = new ArrayList<>();
        this.name = name;
        this.colorCode = "#bcbcbc";
    }

    public SchoolClass(String name, String colorCode){
        this.name = name;
        this.colorCode = translateColor(colorCode);
        this.assignments = new ArrayList<>();
    }

    public SchoolClass() {
        this.assignments = new ArrayList<>();
        this.name = "Null";
        this.colorCode = "#bcbcbc";
    }

    private String translateColor(String chosenColor){
        switch(chosenColor){
            case "Blue":
                this.backgroundID = R.drawable.bluecard;
                return "#0167cc";
            case "Pink":
                this.backgroundID = R.drawable.pinkcard;
                return "#fc98ca";
            case "Green":
                this.backgroundID = R.drawable.greencard;
                return "#99ffcd";
            case "Red":
                this.backgroundID = R.drawable.redcard;
                return "#990134";
            case "Purple":
                this.backgroundID = R.drawable.purplecard;
                return "#330065";
            case "Orange":
                this.backgroundID = R.drawable.orangecard;
                return "#ff6600";
            default:
                this.backgroundID = Color.DKGRAY;
                return "#bcbcbc";
        }
    }

    public void addAssignment(Assignment a){
        assignments.add(a);
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void sortAssignmentsByDate(){
        for(int i = 0; i < assignments.size(); i++){
            if(assignments.get(i).date.compareTo(assignments.get(i + 1).date) > 0){
                Assignment a = assignments.get(i);
                assignments.set(i, assignments.get(i+1));
                assignments.set(i+1, a);
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(assignments);
        dest.writeString(name);
        dest.writeString(colorCode);
        dest.writeInt(listIndex);
        dest.writeInt(backgroundID);
    }

    public static final Parcelable.Creator<SchoolClass> CREATOR
        = new Parcelable.Creator<SchoolClass>() {
                public SchoolClass createFromParcel(Parcel in) {
                    return new SchoolClass(in);
                    }
    
                public SchoolClass[] newArray(int size) {
                    return new SchoolClass[size];
                    }
            };    
}
