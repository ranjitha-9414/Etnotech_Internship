package com.ranjitha.code_execution_engine.dto;

public class RunRequest {

    private String code;
    private String input;

    // Getter
    public String getCode() {
        return code;
    }

    public String getInput() {
        return input;
    }

    // Setter
    public void setCode(String code) {
        this.code = code;
    }

    public void setInput(String input) {
        this.input = input;
    }
}