package com.example.smd_a2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FolderAdapter extends BaseAdapter {
    private ArrayList<Subject.Folder> folders;
    private int subjectIndex;

    public FolderAdapter(ArrayList<Subject.Folder> folders, int subjectIndex) {
        this.folders = folders;
        this.subjectIndex = subjectIndex;
    }

    @Override
    public int getCount() {
        return folders.size();
    }

    @Override
    public Object getItem(int i) {
        return folders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.folder_card, viewGroup, false); // Your XML name
        }

        Subject.Folder folder = folders.get(i);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvDesc = convertView.findViewById(R.id.tvDesc);
        ImageView ivDelete = convertView.findViewById(R.id.ivDelete);
        Button btnOpen = convertView.findViewById(R.id.btnOpen);

        tvTitle.setText(folder.getTitle());
        tvDesc.setText(folder.getImages().size() + " images");

        ivDelete.setOnClickListener(v -> {
            DataClass.subjects.get(this.subjectIndex).getFolders().remove(i);
            notifyDataSetChanged();
        });

        btnOpen.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NotesImageActivity.class);
            intent.putExtra("subjectIndex", this.subjectIndex);
            intent.putExtra("folderIndex", i);

            v.getContext().startActivity(intent);
        });

        return convertView;
    }
}
