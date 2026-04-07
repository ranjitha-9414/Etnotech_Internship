package com.ranjitha.code_execution_engine.repository;

import com.ranjitha.code_execution_engine.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}