package com.example.smd_a2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FoldersActivity extends AppCompatActivity {
    ListView lvFolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_folders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvFolders = findViewById(R.id.lvFolders);

        // Get the list passed from the Fragment
        int position = getIntent().getIntExtra("subjectIndex", -1);

        Subject subject = DataClass.subjects.get(position);

        ArrayList<Subject.Folder> folders = subject.getFolders();

        if (folders != null) {
            FolderAdapter adapter = new FolderAdapter(folders, position);
            lvFolders.setAdapter(adapter);
        }

        TextView tvFolderName = findViewById(R.id.tvFolderName);
        ImageView ivBack = findViewById(R.id.ivBack);

        tvFolderName.setText(subject.getTitle());
        ivBack.setOnClickListener(v -> {
            finish();
        });
    }
}