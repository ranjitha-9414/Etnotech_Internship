package com.ranjitha.code_execution_engine.dto;

public class LeaderboardDTO {

    private int rank;
    private String username;
    private long totalSubmissions;
    private long acceptedSubmissions;
    private double accuracy;

    public LeaderboardDTO(String username, long total, long accepted) {
        this.username = username;
        this.totalSubmissions = total;
        this.acceptedSubmissions = accepted;

        if (total > 0) {
            this.accuracy = (accepted * 100.0) / total;
        } else {
            this.accuracy = 0;
        }
    }
    public double getAccuracy() {
        return accuracy;
    }
    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public String getUsername() { return username; }
    public long getTotalSubmissions() { return totalSubmissions; }
    public long getAcceptedSubmissions() { return acceptedSubmissions; }
}