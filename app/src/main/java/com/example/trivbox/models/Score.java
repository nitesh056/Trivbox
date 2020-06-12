package com.example.trivbox.models;

import java.io.Serializable;

public class Score implements Serializable {
    private int _id;
    private String category, difficulty, type;
    private int point;

    public Score() {}

    public Score(String category, String difficulty, String type, int point) {
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
        this.point = point;
    }

    public Score(String category, String difficulty, String type) {
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getType() {
        return type;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
