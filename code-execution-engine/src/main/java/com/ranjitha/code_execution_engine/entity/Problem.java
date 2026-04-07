package com.ranjitha.code_execution_engine.entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String difficulty;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TestCaseEntity> testCases;

    // getters & setters

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() { return difficulty; }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<TestCaseEntity> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseEntity> testCases) {
        this.testCases = testCases;
    }

    public void setId(Long id2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}