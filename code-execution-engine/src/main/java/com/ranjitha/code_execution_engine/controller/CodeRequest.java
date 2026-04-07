package com.ranjitha.code_execution_engine.controller;

import lombok.Data;

@Data
public class CodeRequest {
    private String code;
    private String input;
}