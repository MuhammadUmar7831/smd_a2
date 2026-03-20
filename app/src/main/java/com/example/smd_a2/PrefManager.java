package com.example.smd_a2;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefManager {
    private static final String PREF_NAME = "app_data";
    private static final String KEY_SUBJECTS = "saved_subjects";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Save the entire list as a JSON string
    public void saveSubjects(ArrayList<Subject> list) {
        String json = gson.toJson(list);
        sharedPreferences.edit().putString(KEY_SUBJECTS, json).apply();
    }

    // Load the JSON string and convert it back to an ArrayList
    public ArrayList<Subject> loadSubjects() {
        String json = sharedPreferences.getString(KEY_SUBJECTS, null);
        if (json == null) return null;

        Type type = new TypeToken<ArrayList<Subject>>() {}.getType();
        return gson.fromJson(json, type);
    }
}