package com.ranjitha.code_execution_engine.repository;

import com.ranjitha.code_execution_engine.dto.LeaderboardDTO;
import com.ranjitha.code_execution_engine.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByProblemIdOrderByIdDesc(Long problemId);

    List<Submission> findAllByOrderByIdDesc();

    // 🔥 NEW
    List<Submission> findByUserUsernameOrderByIdDesc(String username);
    @Query("""
    SELECT new com.ranjitha.code_execution_engine.dto.LeaderboardDTO(
        s.user.username,
        COUNT(s),
        SUM(CASE WHEN s.status = 'ACCEPTED' THEN 1 ELSE 0 END)
    )
    FROM Submission s
    GROUP BY s.user.username
    ORDER BY 
        SUM(CASE WHEN s.status = 'ACCEPTED' THEN 1 ELSE 0 END) DESC,
        COUNT(s) ASC
    """)
    List<LeaderboardDTO> getLeaderboard();
}