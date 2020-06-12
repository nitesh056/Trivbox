package com.example.trivbox.models;

public class Leaderboard implements Comparable<Leaderboard> {
    private String leaderboardID;
    private Score score;
    private String name;

    public Leaderboard() {}

    public Leaderboard(String leaderboardID, Score score, String name) {
        this.leaderboardID = leaderboardID;
        this.score = score;
        this.name = name;
    }

    public String getLeaderboardID() {
        return leaderboardID;
    }

    public Score getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Leaderboard o) {
        return this.getScore().getPoint() - o.getScore().getPoint();
    }

}
