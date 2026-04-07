package com.ranjitha.code_execution_engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class TestCaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String input;

    @Column(columnDefinition = "TEXT")
    private String expectedOutput;

    private boolean sample; // ✅ IMPORTANT

    @ManyToOne
    @JoinColumn(name = "problem_id")
    @JsonManagedReference
    @JsonIgnore
    private Problem problem;

    // ✅ GETTERS & SETTERS

    public Long getId() { return id; }

    public String getInput() { return input; }

    public void setInput(String input) { this.input = input; }

    public String getExpectedOutput() { return expectedOutput; }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public boolean isSample() {  // 🔥 MUST BE isSample()
        return sample;
    }

    public void setSample(boolean sample) {
        this.sample = sample;
    }

    public Problem getProblem() { return problem; }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }
}