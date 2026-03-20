package com.example.smd_a2;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    private ArrayList<Subject> subjectList;

    public SubjectAdapter(ArrayList<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_card, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.tvTitle.setText(subject.getTitle());
        holder.tvDesc.setText(subject.getFolders().size() + " folders");
        holder.ivIcon.setImageResource(subject.getIcon());

//        holder.btnOpen.setOnClickListener(v -> {
//            Intent intent = new Intent(v.getContext(), FoldersActivity.class);
//            intent.putExtra("subjectIndex", position);
//
//            v.getContext().startActivity(intent);
//            notifyDataSetChanged();
//        });
        // Inside onBindViewHolder in SubjectAdapter.java
        holder.btnOpen.setOnClickListener(v -> {
            // 1. Update the global selection
            DataClass.selectedSubjectIndex = position;

            // 2. Tell MainActivity to swipe to the Folders tab (index 1)
            if (v.getContext() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) v.getContext();
                mainActivity.viewPager2.setCurrentItem(1, true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public void updateData(ArrayList<Subject> newList) {
        this.subjectList = newList;
        notifyDataSetChanged();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        ImageView ivIcon;
        Button btnOpen;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            btnOpen = itemView.findViewById(R.id.btnOpen);
        }
    }
}
