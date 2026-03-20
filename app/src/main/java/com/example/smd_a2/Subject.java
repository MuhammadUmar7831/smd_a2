package com.example.smd_a2;

import java.io.Serializable;
import java.util.ArrayList;

public class Subject {
    private String title;
    private ArrayList<Folder> folders;
    private int icon;

    public Subject(String title, ArrayList<Folder> folders, int icon) {
        this.title = title;
        this.folders = folders;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public int getIcon() {
        return icon;
    }


    public class Folder {
        private String title;
        private ArrayList<String> images;

        public Folder(String title, ArrayList<String> images) {
            this.title = title;
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public ArrayList<String> getImages() {
            return images;
        }
    }
}
