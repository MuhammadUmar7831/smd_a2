package com.example.smd_a2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabLayoutMediator tabLayoutMediator;
    ViewPager2 viewPager2;

    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hookInitializer();
        initializeAppData();

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Subject");
                            break;
                        case 1:
                            tab.setText("Folders");
                            break;
                        case 2:
                            tab.setText("Settings");
                            break;
                        default:
                            tab.setText("Subject");
                            break;
                    }
                });

        tabLayoutMediator.attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) { // Subject Tab
                    // Get the fragment currently at position 0
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("f" + position);
                    if (fragment instanceof SubjectFragment) {
                        ((SubjectFragment) fragment).refreshUI();
                    }
                }
            }
        });
    }

    private void hookInitializer() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
    }

    private void initializeAppData() {
        SharedPreferences prefs = getSharedPreferences(DataClass.PREFS_NAME, Context.MODE_PRIVATE);
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        Gson gson = new Gson();

        if (isFirstRun) {
            // 1. Generate the data for the first time
            ArrayList<Subject> mockList = getMockData();

            // 2. Convert to JSON and Save
            String json = gson.toJson(mockList);
            prefs.edit()
                    .putString("saved_subjects", json)
                    .putBoolean("isFirstRun", false) // Mark as done
                    .apply();

            DataClass.subjects = mockList;
        } else {
            // 3. Not first run, just load from SharedPreferences
            String json = prefs.getString("saved_subjects", null);
            if (json != null) {
                Type type = new TypeToken<ArrayList<Subject>>() {}.getType();
                DataClass.subjects = gson.fromJson(json, type);
            } else {
                // Fallback: if JSON is somehow null, create empty list
                DataClass.subjects = new ArrayList<>();
            }
        }
    }

    private ArrayList<Subject> getMockData() {
        DataClass.subjects = new ArrayList<>();
        String[] titles = {"Data Structures", "Operating Systems", "Artificial Intelligence", "Database"};

        for (String title : titles) {
            ArrayList<Subject.Folder> folders = new ArrayList<>();
            // Folders: Lectures, Assignments, Prepare Quizes, Lab Work
            String[] folderNames = {"Lectures", "Assignments", "Prepare Quizes", "Lab Work"};

            String[] internetImages = {
                    "https://images.unsplash.com/photo-1517842645767-c639042777db",
                    "https://images.unsplash.com/photo-1503551723145-6c040742065b",
                    "https://images.unsplash.com/photo-1453728013993-6d66e9c9123a",
                    "https://images.unsplash.com/photo-1434030216411-0b793f4b4173",
                    "https://images.unsplash.com/photo-1516979187457-637abb4f9353",
                    "https://images.unsplash.com/photo-1497633762265-9d179a990aa6"
            };

            for (String fName : folderNames) {
                // Each with 6 dummy image paths
                ArrayList<String> images = new ArrayList<>();
                for (String image : internetImages) images.add(image);

                // Note: Subject.Folder needs to be static or instantiated via Subject instance
                folders.add(new Subject(null, null, 0).new Folder(fName, images));
            }
            int icon = 0;
            if (title.equals("Data Structures")) {
                icon = (R.drawable.book);
            } else if (title.equals("Operating Systems")) {
                icon = (R.drawable.cpu);
            } else if (title.equals("Artificial Intelligence")) {
                icon = (R.drawable.brain);
            } else if (title.equals("Database")) {
                icon = (R.drawable.database);
            }

            DataClass.subjects.add(new Subject(title, folders, icon));
        }
        return DataClass.subjects;
    }

}