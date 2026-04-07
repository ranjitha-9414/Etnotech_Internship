package com.ranjitha.code_execution_engine.repository;

import com.ranjitha.code_execution_engine.entity.TestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCaseEntity, Long> {

    List<TestCaseEntity> findByProblemId(Long problemId);
}