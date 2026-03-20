package com.example.smd_a2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SubjectFragment extends Fragment {

    RecyclerView rvSubjects;
    SubjectAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject, container, false);

        rvSubjects = view.findViewById(R.id.rvSubjects);

        rvSubjects.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 2));

        adapter = new SubjectAdapter(DataClass.subjects);
        rvSubjects.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the adapter whenever the user swipes back to this tab
        Toast.makeText(getContext(), "This", Toast.LENGTH_SHORT);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void refreshUI() {
        if (adapter != null) {
            // Explicitly pass the current state of the global list
            adapter.updateData(DataClass.subjects);
        }
    }
}