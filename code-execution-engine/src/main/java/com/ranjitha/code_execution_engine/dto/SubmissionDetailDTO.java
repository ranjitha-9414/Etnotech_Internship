package com.ranjitha.code_execution_engine.dto;

import com.ranjitha.code_execution_engine.entity.Submission;
import java.util.List;

public class SubmissionDetailDTO {

    public Long id;
    public String status;
    public int passedTestCases;
    public int totalTestCases;
    public String code;

    // 🔥 ADD THIS
    public List<TestCaseResultDTO> testCases;

    public SubmissionDetailDTO(Submission s, List<TestCaseResultDTO> testCases) {
        this.id = s.getId();
        this.status = s.getStatus();
        this.passedTestCases = s.getPassedTestCases();
        this.totalTestCases = s.getTotalTestCases();
        this.code = s.getCode();
        this.testCases = testCases;
    }
}