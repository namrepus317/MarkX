package com.example.christopher.markx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class FileIO {

    private String fileName;
    private FileInputStream fis;
    private FileOutputStream fos;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Context appContext;

    public void writeSaveData(Object object){
        try{
            fos = appContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Object getSaveData(){
        Object obj = null;
        try{
            fis = appContext.openFileInput(fileName);
            ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            ois.close();
            fis.close();

        } catch(Exception e){
            e.printStackTrace();
        }
        return obj;
    }


    public FileIO(Context context, String fileName){
        this.appContext = context;
        this.fileName = fileName;
    }



}
