package com.example.smd_a2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NotesImageActivity extends AppCompatActivity {

    private ImageAdapter adapter;
    private final List<Uri> imageList = new ArrayList<>();
    private Subject.Folder currentFolder; // Store reference to update the data model

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    // 1. Update UI List
                    imageList.add(uri);

                    // 2. Update your Data Model (DataClass)
                    if (currentFolder != null) {
                        currentFolder.getImages().add(uri.toString());
                    }

                    adapter.notifyItemInserted(imageList.size() - 1);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes_image);

        RecyclerView rvNotesImages = findViewById(R.id.rvNotesImages);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddImage);


        TextView tvFolderName = findViewById(R.id.tvFolderName);
        ImageView ivBack = findViewById(R.id.ivBack);

        // Extract indices from Intent
        Intent intent = getIntent();
        int subjectIndex = intent.getIntExtra("subjectIndex", -1);
        int folderIndex = intent.getIntExtra("folderIndex", -1);

        // Fetch data from your static DataClass
        if (subjectIndex != -1 && folderIndex != -1) {
            Subject subject = DataClass.subjects.get(subjectIndex);
            currentFolder = subject.getFolders().get(folderIndex);

            // --- LOAD IMAGES FROM INTENT DATA ---
            for (String path : currentFolder.getImages()) {
                // Parse the string path into a Uri
                imageList.add(Uri.parse(path));
            }

            tvFolderName.setText(currentFolder.getTitle());
            ivBack.setOnClickListener(v -> {
                finish();
            });
        }

        // Requirement: 2 columns Grid
        rvNotesImages.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ImageAdapter(imageList, new ImageAdapter.OnImageActionListener() {
            @Override
            public void onShare(Uri uri) {
                shareImageIntent(uri);
            }

            @Override
            public void onDelete(int position) {
                // Update local list
                imageList.remove(position);
                // Update your Data Model
                if (currentFolder != null) {
                    currentFolder.getImages().remove(position);
                }
                adapter.notifyItemRemoved(position);
            }
        });

        rvNotesImages.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> galleryLauncher.launch("image/*"));
    }

    private void shareImageIntent(Uri uri) {
        if (uri == null) {
            Toast.makeText(this, "Image does not exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share note via..."));
    }
}