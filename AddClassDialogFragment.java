package com.example.christopher.markx;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Christopher on 11/21/2017.
 */

public class AddClassDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {

    String selectedColor;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedColor = (String)parent.getItemAtPosition(position);
        Log.d("TestPointMew", selectedColor);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface AddClassDialogListener{
        public void onDialogPositiveClick(SchoolClass schoolClass);
    }

    AddClassDialogListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try{
            mListener = (AddClassDialogListener)activity;
        } catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + "must implement AddClassDialogListener");
        }
    }

    public AddClassDialogFragment(){

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create new class");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_class_dialog_layout, null);
        builder.setView(dialogView);
        final EditText className = (EditText)dialogView.findViewById(R.id.addClassName);

        final Spinner colorSpinner = (Spinner)dialogView.findViewById(R.id.colorSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Class_Color_Options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
        colorSpinner.setOnItemSelectedListener(this);

        builder.setTitle("Add Class");
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SchoolClass c = new SchoolClass(className.getText().toString(), selectedColor);
                mListener.onDialogPositiveClick(c);
                dialog.dismiss();
            }
        });
       return builder.create();
    }
}
