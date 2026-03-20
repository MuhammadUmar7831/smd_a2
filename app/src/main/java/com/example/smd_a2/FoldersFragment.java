package com.example.smd_a2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class FoldersFragment extends Fragment {
    ListView lvFolders;
    TextView tvEmptyState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folders, container, false);
        lvFolders = view.findViewById(R.id.lvFolders);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFolders();
    }

    // Call this from MainActivity when tab changes
    public void refreshFolders() {
        if (DataClass.selectedSubjectIndex == -1 || DataClass.subjects.isEmpty()) {
            lvFolders.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
        } else {
            lvFolders.setVisibility(View.VISIBLE);
            tvEmptyState.setVisibility(View.GONE);

            Subject selectedSubject = DataClass.subjects.get(DataClass.selectedSubjectIndex);
            FolderAdapter adapter = new FolderAdapter(selectedSubject.getFolders(), DataClass.selectedSubjectIndex);
            lvFolders.setAdapter(adapter);
        }
    }
}