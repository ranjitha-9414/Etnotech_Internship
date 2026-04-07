package com.ranjitha.code_execution_engine.executor;

import java.util.List;

public record JudgeResult(
        String status,
        int passedTestCases,
        int totalTestCases,
        String output,
        String expectedOutput,
        String error,
        List<TestCaseResult> testCaseResults   // 🔥 NEW
) {}