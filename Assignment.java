package com.example.christopher.markx;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Christopher on 10/21/2017.
 */

public class Assignment implements Parcelable, Serializable {

    public String name;
    public String dueDate;
    public String notes;
    public Calendar date;

    public Assignment(String name, String dueDate, String notes, Context context) {
        this.name = name;
        this.dueDate = dueDate;

        if(notes == ""){
            this.notes = "No Notes";
        }
        else {
            this.notes = notes;
        }
        this.date = Calendar.getInstance();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public Assignment(Parcel in){
        this.name =  in.readString();
        this.dueDate = in.readString();
        this.notes = in.readString();
        //this.date.setTimeInMillis(in.readLong());
    }


    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String  dueDate) {
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(dueDate);
        dest.writeString(notes);
        //dest.writeLong(date.getTimeInMillis());
    }

    public static final Parcelable.Creator<Assignment> CREATOR
            = new Parcelable.Creator<Assignment>() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        public Assignment createFromParcel(Parcel in) {
            return new Assignment(in);
        }

        public Assignment[] newArray(int size) {
            return new Assignment[size];
        }
    };
}
