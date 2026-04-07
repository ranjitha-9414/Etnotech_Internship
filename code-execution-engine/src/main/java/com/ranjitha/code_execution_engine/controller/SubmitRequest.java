package com.ranjitha.code_execution_engine.controller;

import lombok.Data;

@Data
public class SubmitRequest {
    private String code;
    private Long problemId;
}