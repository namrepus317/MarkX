package com.example.christopher.markx;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 11/20/2017.
 */

public class User implements Parcelable, Serializable {

    String name;
    List<SchoolClass> classes;
    List<SchoolClass> defaultClasses = new ArrayList<>();
    int numberOfClasses;
    int selectedClass;


    public User(){
        this.name = "Null";
        this.classes = defaultClasses;
        this.numberOfClasses = classes.size();
    }

    public User(String name, List<SchoolClass> classes) {
        this.name = name;
        this.classes = classes;
        this.numberOfClasses = classes.size();
    }

    public void addClass(SchoolClass c){
        classes.add(c);
    }

    protected User(Parcel in) {
        name = in.readString();
        classes = in.createTypedArrayList(SchoolClass.CREATOR);
        numberOfClasses = in.readInt();
        selectedClass = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(classes);
        dest.writeInt(numberOfClasses);
        dest.writeInt(selectedClass);
    }
}
