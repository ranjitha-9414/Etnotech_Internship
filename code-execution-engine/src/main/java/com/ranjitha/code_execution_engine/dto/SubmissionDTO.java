package com.ranjitha.code_execution_engine.dto;

public class SubmissionDTO {

    private Long id;
    private String problemTitle;
    private String status;
    private int passedTestCases;
    private int totalTestCases;

    public SubmissionDTO(Long id, String problemTitle, String status,
                         int passed, int total) {
        this.id = id;
        this.problemTitle = problemTitle;
        this.status = status;
        this.passedTestCases = passed;
        this.totalTestCases = total;
    }

    public Long getId() { return id; }
    public String getProblemTitle() { return problemTitle; }
    public String getStatus() { return status; }
    public int getPassedTestCases() { return passedTestCases; }
    public int getTotalTestCases() { return totalTestCases; }
}