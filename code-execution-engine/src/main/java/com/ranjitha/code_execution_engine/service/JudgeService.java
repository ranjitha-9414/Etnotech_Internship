package com.ranjitha.code_execution_engine.service;

import com.ranjitha.code_execution_engine.entity.TestCaseEntity;
import com.ranjitha.code_execution_engine.executor.CodeExecutionService;
import com.ranjitha.code_execution_engine.executor.JudgeResult;
import com.ranjitha.code_execution_engine.repository.TestCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgeService {

    private final TestCaseRepository testCaseRepository;
    private final CodeExecutionService codeExecutionService;

    public JudgeService(TestCaseRepository testCaseRepository,
                        CodeExecutionService codeExecutionService) {
        this.testCaseRepository = testCaseRepository;
        this.codeExecutionService = codeExecutionService;
    }

    public JudgeResult judge(Long problemId, String code) {

        List<TestCaseEntity> testCases =
                testCaseRepository.findByProblemId(problemId);

        int passed = 0;
        String lastOutput = "";
        String expected = "";
        String error = null;

        for (TestCaseEntity tc : testCases) {

            try {
                String output = codeExecutionService.runCode(code, tc.getInput());

                lastOutput = output;
                expected = tc.getExpectedOutput();

                if (output.trim().equals(tc.getExpectedOutput().trim())) {
                    passed++;
                } else {
                    return new JudgeResult(
                            "WRONG_ANSWER",
                            passed,
                            testCases.size(),
                            output,
                            tc.getExpectedOutput(),
                            null, null
                    );
                }

            } catch (Exception e) {
                return new JudgeResult(
                        "RUNTIME_ERROR",
                        passed,
                        testCases.size(),
                        "",
                        tc.getExpectedOutput(),
                        e.getMessage(), null
                );
            }
        }

        return new JudgeResult(
                "ACCEPTED",
                passed,
                testCases.size(),
                lastOutput,
                expected,
                error, null
        );
    }
}