package com.ranjitha.code_execution_engine.controller;

import lombok.Data;
import java.util.List;

import com.ranjitha.code_execution_engine.dto.TestCaseDTO;

@Data
public class JudgeRequest {
    private String code;
    private List<TestCaseDTO> testCases;
    public String getExpectedOutput() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getExpectedOutput'");
    }
    public String getInput() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInput'");
    }
}