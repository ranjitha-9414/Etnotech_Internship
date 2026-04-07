package com.ranjitha.code_execution_engine.service;

import com.ranjitha.code_execution_engine.entity.Problem;
import com.ranjitha.code_execution_engine.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    private final ProblemRepository problemRepo;

    public ProblemService(ProblemRepository problemRepo) {
        this.problemRepo = problemRepo;
    }

    public Problem saveProblem(Problem problem) {
        return problemRepo.save(problem);
    }

    public Problem getProblem(Long id) {
        return problemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
    }

    // 🔥 NEW METHOD
    public List<Problem> getAllProblems() {
        return problemRepo.findAll();
    }
}