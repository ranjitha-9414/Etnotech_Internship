package com.ranjitha.code_execution_engine.dto;

public class TestCaseResultDTO {

    public int index;
    public String status;   // PASS / FAIL
    public String output;
    public String expected;

    public TestCaseResultDTO(int index, String status, String output, String expected) {
        this.index = index;
        this.status = status;
        this.output = output;
        this.expected = expected;
    }
}