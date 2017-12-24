package com.example.christopher.markx;

/**
 * Created by Christopher on 11/22/2017.
 */

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuizletResource implements Parcelable {

    String qParam;
    String termParam;

    Context context;
    Boolean isSetsLoaded;

    ArrayList<String> setTitles;
    int[] termCounts = new int[5];

    public JSONArray setsArray;

    private OnSetsLoadedListener listener;

    //Constructors

    public QuizletResource(Context context){
        this.context = context;
        this.isSetsLoaded = false;
    }

    //Getters and Setters
    public String getqParam() {
        return qParam;
    }

    public void setqParam(String qParam) {
        this.qParam = qParam;
    }

    public String getTermParam() {
        return termParam;
    }

    public void setTermParam(String termParam) {
        this.termParam = termParam;
    }

    //Request Methods
    public String generateURL(){
        String url = "https://api.quizlet.com/2.0/search/sets/?client_id=KudDPEHR9E"
                + "&q=" + qParam + "term=&" + termParam;
        return url;
    }

    public void loadQuizletSets(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, generateURL(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            setsArray = response.getJSONArray("sets");
                            getSetNames();
                            getNumberOfTerms();
                            Toast.makeText(context, "Quizlet sets loaded.", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listener.OnSetsLoaded(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    //Methods for Data Collection
    public void getSetNames(){
        ArrayList<String> strings = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            try {
                strings.add(setsArray.getJSONObject(i).getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        setTitles = strings;
    }

    public void getNumberOfTerms(){
        int[] num = new int[5] ;
        for(int i = 0; i < 5; i++){
            try {
                num[i] = setsArray.getJSONObject(i).getInt("term_count");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        termCounts = num;
    }

    //Callback Interface
    public interface OnSetsLoadedListener{
        public void OnSetsLoaded(JSONObject response);
    }

    public void setOnSetsLoadedListener(OnSetsLoadedListener listener){
        this.listener = listener;
    }

    //Parcel Methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(qParam);
        dest.writeString(termParam);
        dest.writeStringList(setTitles);
        dest.writeIntArray(termCounts);
    }

    protected QuizletResource(Parcel in) {
        qParam = in.readString();
        termParam = in.readString();
        setTitles = in.createStringArrayList();
        termCounts = in.createIntArray();
    }

    public static final Creator<QuizletResource> CREATOR = new Creator<QuizletResource>() {
        @Override
        public QuizletResource createFromParcel(Parcel in) {
            return new QuizletResource(in);
        }

        @Override
        public QuizletResource[] newArray(int size) {
            return new QuizletResource[size];
        }
    };



}
