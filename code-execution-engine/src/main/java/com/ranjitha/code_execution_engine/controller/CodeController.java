package com.ranjitha.code_execution_engine.controller;

import com.ranjitha.code_execution_engine.dto.RunRequest;
import com.ranjitha.code_execution_engine.dto.SubmissionDTO;
import com.ranjitha.code_execution_engine.dto.SubmissionDetailDTO;
import com.ranjitha.code_execution_engine.dto.TestCaseResultDTO;
import com.ranjitha.code_execution_engine.entity.Problem;
import com.ranjitha.code_execution_engine.entity.Submission;
import com.ranjitha.code_execution_engine.entity.TestCaseEntity;
import com.ranjitha.code_execution_engine.executor.CodeExecutionService;
import com.ranjitha.code_execution_engine.executor.ExecutionResult;
import com.ranjitha.code_execution_engine.executor.JudgeResult;
import com.ranjitha.code_execution_engine.executor.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
public class CodeController {

    private final CodeExecutionService service;

    public CodeController(CodeExecutionService service) {
        this.service = service;
    }

   
    @PostMapping("/judge")
public JudgeResult judge(@RequestBody JudgeRequest request) {
    TestCase testCase = new TestCase(
            request.getInput(),
            request.getExpectedOutput()
    );

    return service.judgeCode(
            request.getCode(),
            "Main",
            testCase
    );
}
@PostMapping("/judge-multiple")
public JudgeResult judgeMultiple(@RequestBody JudgeRequest request) {

    java.util.List<TestCase> testCases = request.getTestCases()
            .stream()
            .map(tc -> new TestCase(tc.getInput(), tc.getExpectedOutput()))
            .toList();

    return service.judgeMultipleTestCases(
            request.getCode(),
            "Main",
            testCases
    );
    }
    @PostMapping("/submit")
    public JudgeResult submit(@RequestBody SubmitRequest request) {

    return service.judgeFromDatabase(
            request.getCode(),
            "Main",
            request.getProblemId()
    );
    }
@PostMapping("/submit-save")
public Map<String, Object> submitSave(@RequestBody Map<String, Object> req) {

    Map<String, Object> res = new HashMap<>();

    try {
        Long problemId = Long.valueOf(req.get("problemId").toString());
        String code = (String) req.get("code");
        String username = (String) req.get("username"); // 🔥 GET USER

        Submission submission = service.submitAndSaveWithUser(code, "Main", problemId, username);

        res.put("status", submission.getStatus());
        res.put("passedTestCases", submission.getPassedTestCases());
        res.put("totalTestCases", submission.getTotalTestCases());

    } catch (Exception e) {
        e.printStackTrace();

        res.put("status", e.getMessage());
        res.put("passedTestCases", 0);
        res.put("totalTestCases", 0);
    }

    return res;
}
    @GetMapping("/submissions/{problemId}")
    public java.util.List<com.ranjitha.code_execution_engine.entity.Submission> 
    getSubmissions(@PathVariable Long problemId) {

        return service.getSubmissionsByProblem(problemId);
    }
    @GetMapping("/submissions")
    public java.util.List<com.ranjitha.code_execution_engine.entity.Submission> 
    getAllSubmissions() {

        return service.getAllSubmissions();
    }
    @GetMapping("/my-submissions")
    public List<SubmissionDTO> getMySubmissions() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return service.getSubmissionsByUser(username); // ✅ DTO
    }
    @GetMapping("/leaderboard")
    public java.util.List<com.ranjitha.code_execution_engine.dto.LeaderboardDTO> leaderboard() {
        return service.getLeaderboard();
    }
    @PostMapping("/run")
    public ExecutionResult runCode(@RequestBody RunRequest request) {
        return service.executeJavaCode(
                request.getCode(),
                "Main",
                request.getInput()
        );
    }
   @GetMapping("/submission/{id}")
public SubmissionDetailDTO getSubmission(@PathVariable Long id) {

    Submission s = service.getSubmissionById(id);
    List<TestCaseResultDTO> results = service.getTestCaseResults(id);

    return new SubmissionDetailDTO(s, results);
}
    
    

}