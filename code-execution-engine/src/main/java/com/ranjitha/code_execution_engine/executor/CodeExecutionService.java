package com.ranjitha.code_execution_engine.executor;

import org.springframework.security.core.context.SecurityContextHolder;
import com.ranjitha.code_execution_engine.entity.User;
import com.ranjitha.code_execution_engine.entity.TestCaseEntity;
import com.ranjitha.code_execution_engine.repository.TestCaseRepository;
import com.ranjitha.code_execution_engine.repository.UserRepository;

import org.springframework.stereotype.Service;
import com.ranjitha.code_execution_engine.repository.SubmissionRepository;
import com.ranjitha.code_execution_engine.entity.Submission;
import com.ranjitha.code_execution_engine.dto.SubmissionDTO;
import com.ranjitha.code_execution_engine.dto.TestCaseResultDTO;
import com.ranjitha.code_execution_engine.entity.Problem;
import com.ranjitha.code_execution_engine.repository.ProblemRepository;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class CodeExecutionService {

    private static final int TIME_LIMIT_SECONDS = 5;

    private final TestCaseRepository testCaseRepo;
    private final SubmissionRepository submissionRepo;
    private final ProblemRepository problemRepo;
    private final UserRepository userRepo;

    public CodeExecutionService(
            TestCaseRepository testCaseRepo,
            SubmissionRepository submissionRepo,
            ProblemRepository problemRepo,
            UserRepository userRepo
    ) {
        this.testCaseRepo = testCaseRepo;
        this.submissionRepo = submissionRepo;
        this.problemRepo = problemRepo;
        this.userRepo = userRepo;
    }

    // 🔹 EXECUTE CODE
    public ExecutionResult executeJavaCode(String code, String className, String input) {
        try {
            Path javaFile = FileManager.createJavaFile(code, className);
            Path directory = javaFile.getParent();

            // Compile
            Process compile = new ProcessBuilder("javac", javaFile.toString())
                    .directory(directory.toFile())
                    .start();

            String compileError = readStream(compile.getErrorStream());
            compile.waitFor();

            if (!compileError.isEmpty()) {
                return new ExecutionResult("", compileError, false);
            }

            // Run
            Process run = new ProcessBuilder(
                    "java", "-Xmx64m", "-cp", directory.toString(), className
            ).directory(directory.toFile()).start();

            // Input
            if (input != null && !input.isEmpty()) {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(run.getOutputStream())
                );
                writer.write(input);
                writer.newLine();
                writer.flush();
                writer.close();
            }

            ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<String> outputFuture = executor.submit(() -> readStream(run.getInputStream()));
            Future<String> errorFuture = executor.submit(() -> readStream(run.getErrorStream()));

            String output;
            String error;

            try {
                output = outputFuture.get(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
                error = errorFuture.get(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                run.destroyForcibly();
                return new ExecutionResult("", "Time Limit Exceeded", false);
            }

            executor.shutdown();

            if (!error.isEmpty()) {
                return new ExecutionResult("", error, false);
            }

            return new ExecutionResult(output, "", true);

        } catch (Exception e) {
            return new ExecutionResult("", e.getMessage(), false);
        }
    }

    // 🔥 JUDGE FROM DATABASE (FINAL)
 public JudgeResult judgeFromDatabase(String code, String className, Long problemId) {

    List<TestCaseEntity> testCases = testCaseRepo.findByProblemId(problemId);

    int passed = 0;
    int index = 1;

    List<TestCaseResult> results = new java.util.ArrayList<>();

    for (TestCaseEntity tc : testCases) {

        ExecutionResult execResult = executeJavaCode(code, className, tc.getInput());

        if (!execResult.isSuccess()) {
            return new JudgeResult(
                    "RUNTIME_ERROR",
                    passed,
                    testCases.size(),
                    "",
                    "",
                    execResult.getError(),
                    results
            );
        }

        String output = normalize(execResult.getOutput());
        String expected = normalize(tc.getExpectedOutput());

        if (output.equals(expected)) {

            passed++;

            results.add(new TestCaseResult(
                    index,
                    "PASS",
                    output,
                    expected
            ));

        } else {

            results.add(new TestCaseResult(
                    index,
                    "FAIL",
                    output,
                    expected
            ));

            return new JudgeResult(
                    "WRONG_ANSWER",
                    passed,
                    testCases.size(),
                    output,
                    expected,
                    null,
                    results
            );
        }

        index++;
    }

    return new JudgeResult(
            "ACCEPTED",
            passed,
            testCases.size(),
            "",
            "",
            null,
            results
    );
}

    // 🔥 SUBMIT + SAVE
   
public Submission submitAndSave(String code, String className, Long problemId) {

    // 🔥 FIX: handle user safely
    String username = "guest";

    try {
        username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    } catch (Exception e) {
        System.out.println("Auth not found, using guest");
    }

    User user = userRepo.findFirstByUsernameIgnoreCase(username)
            .orElseGet(() -> userRepo.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No user in DB")));

    // 🔥 Run judge
    JudgeResult result = judgeFromDatabase(code, className, problemId);

    // 🔥 Get problem
    Problem problem = problemRepo.findById(problemId)
            .orElseThrow(() -> new RuntimeException("Problem not found"));

    // 🔥 Save submission
    Submission submission = new Submission();
    submission.setCode(code);
    submission.setStatus(result.status());
    submission.setPassedTestCases(result.passedTestCases());
    submission.setTotalTestCases(result.totalTestCases());
    submission.setProblem(problem);
    submission.setUser(user);

    return submissionRepo.save(submission);
}

    // 🔹 GET submissions by problem
    public List<Submission> getSubmissionsByProblem(Long problemId) {
        return submissionRepo.findByProblemIdOrderByIdDesc(problemId);
    }

    // 🔹 GET all submissions
    public List<Submission> getAllSubmissions() {
        return submissionRepo.findAllByOrderByIdDesc();
    }

    // 🔹 GET user submissions
    public List<SubmissionDTO> getSubmissionsByUser(String username) {

    List<Submission> list = submissionRepo.findAll(); // 🔥 FIX

    return list.stream().map(s ->
            new SubmissionDTO(
                    s.getId(),
                    s.getProblem().getTitle(),
                    s.getStatus(),
                    s.getPassedTestCases(),
                    s.getTotalTestCases()
            )
    ).toList();
}
public Submission submitAndSaveWithUser(String code, String className, Long problemId, String username) {

    // 🔥 get correct user
    User user = userRepo.findFirstByUsernameIgnoreCase(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    JudgeResult result = judgeFromDatabase(code, className, problemId);

    Problem problem = problemRepo.findById(problemId)
            .orElseThrow(() -> new RuntimeException("Problem not found"));

    Submission submission = new Submission();
    submission.setCode(code);
    submission.setStatus(result.status());
    submission.setPassedTestCases(result.passedTestCases());
    submission.setTotalTestCases(result.totalTestCases());
    submission.setProblem(problem);
    submission.setUser(user);

    return submissionRepo.save(submission);
}
public Submission getSubmissionById(Long id) {
    return submissionRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Submission not found"));
}
public List<TestCaseResultDTO> getTestCaseResults(Long submissionId) {

    // 🔥 TEMP (dummy data — replace later with real)
    List<TestCaseResultDTO> list = new ArrayList<>();

    list.add(new TestCaseResultDTO(1, "PASS", "120", "120"));
    list.add(new TestCaseResultDTO(2, "PASS", "720", "720"));

    return list;
}
    // 🔹 LEADERBOARD
    public List<com.ranjitha.code_execution_engine.dto.LeaderboardDTO> getLeaderboard() {

        List<com.ranjitha.code_execution_engine.dto.LeaderboardDTO> list =
                submissionRepo.getLeaderboard();

        int rank = 1;

        for (com.ranjitha.code_execution_engine.dto.LeaderboardDTO dto : list) {
            dto.setRank(rank++);
        }

        return list;
    }

    // 🔹 NORMALIZE OUTPUT
    private String normalize(String s) {
        return s == null ? "" : s.trim().replaceAll("\\s+", " ");
    }

    // 🔹 READ STREAM
    private String readStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }

        return result.toString();
    }

    public JudgeResult judgeMultipleTestCases(String code, String string, List<TestCase> testCases) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'judgeMultipleTestCases'");
    }

    public JudgeResult judgeCode(String code, String string, TestCase testCase) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'judgeCode'");
    }

    public String runCode(String code, String input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'runCode'");
    }

    public Problem getProblem(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProblem'");
    }
}