package com.ranjitha.code_execution_engine.executor;

public record TestCaseResult(
        int testCaseNumber,
        String status,
        String output,
        String expectedOutput
) {}