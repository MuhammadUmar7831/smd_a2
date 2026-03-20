package com.example.smd_a2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {
    private SwitchMaterial switchDark, switchPreview;
    private MaterialCardView cardClearNotes, cardResetData;
    private SharedPreferences sharedPreferences;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(DataClass.PREFS_NAME, Context.MODE_PRIVATE);

        // 2. Bind Views
        switchDark = view.findViewById(R.id.switchDark);
        switchPreview = view.findViewById(R.id.switchImagePreview);
        cardClearNotes = view.findViewById(R.id.iconClear).getRootView().findViewById(R.id.titleClear).getParent().getParent() instanceof MaterialCardView ?
                (MaterialCardView) view.findViewById(R.id.titleClear).getParent().getParent() : null;

        // Note: It's safer to give your CardViews IDs in XML (e.g., android:id="@+id/cardClearNotes")
        // For now, let's assume you add IDs to the CardViews for easier access:
        cardClearNotes = view.findViewById(R.id.cardClearNotes); // Add this ID in your XML
        cardResetData = view.findViewById(R.id.cardResetData);   // Add this ID in your XML

        // 3. Load Saved States
        loadSettings();

        // 4. Set Event Listeners
        setupListeners();
    }

    private void loadSettings() {
        boolean isDarkMode = sharedPreferences.getBoolean(DataClass.KEY_DARK_MODE, false);
        boolean isPreviewEnabled = sharedPreferences.getBoolean(DataClass.KEY_IMAGE_PREVIEW, true);

        switchDark.setChecked(isDarkMode);
        switchPreview.setChecked(isPreviewEnabled);
    }

    private void setupListeners() {
        // Dark Mode Toggle
        switchDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveBooleanSetting(DataClass.KEY_DARK_MODE, isChecked);
            Toast.makeText(getContext(), "Dark Mode: " + isChecked, Toast.LENGTH_SHORT).show();
            // Logic to change theme would go here
        });

        // Image Preview Toggle
        switchPreview.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveBooleanSetting(DataClass.KEY_IMAGE_PREVIEW, isChecked);
        });

        // Clear All Notes Action
        if (cardClearNotes != null) {
            cardClearNotes.setOnClickListener(v -> {
                // Ignore notes functionality for now as requested
                DataClass.subjects = new ArrayList<>();
                Gson gson = new Gson();
                String emptyJson = gson.toJson(DataClass.subjects); // Returns "[]"

                sharedPreferences.edit()
                        .putString("saved_subjects", emptyJson)
                        .apply();
                Toast.makeText(getContext(), "Notes cleared (Simulation)", Toast.LENGTH_SHORT).show();
            });
        }

        // Reset App Data Action
        if (cardResetData != null) {
            cardResetData.setOnClickListener(v -> {
                resetDefaultSettings();
                Toast.makeText(getContext(), "Settings Reset to Default", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void saveBooleanSetting(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    private void resetDefaultSettings() {
        // Reset SharedPreferences to defaults
        sharedPreferences.edit()
                .putBoolean(DataClass.KEY_DARK_MODE, false)
                .putBoolean(DataClass.KEY_IMAGE_PREVIEW, true)
                .apply();

        // Refresh UI
        loadSettings();
    }
}