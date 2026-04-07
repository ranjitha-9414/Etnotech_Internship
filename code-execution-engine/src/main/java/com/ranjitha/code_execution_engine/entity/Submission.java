package com.ranjitha.code_execution_engine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String code;

    private String status;

    private int passedTestCases;
    private int totalTestCases;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    // 🔥 NEW FIELD
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}