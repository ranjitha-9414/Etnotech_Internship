package com.ranjitha.code_execution_engine.service;

import com.ranjitha.code_execution_engine.dto.LeaderboardDTO;
import com.ranjitha.code_execution_engine.repository.SubmissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {

    private final SubmissionRepository submissionRepo;

    public LeaderboardService(SubmissionRepository submissionRepo) {
        this.submissionRepo = submissionRepo;
    }

    public List<LeaderboardDTO> getLeaderboard() {

        List<LeaderboardDTO> list = submissionRepo.getLeaderboard(); // 🔥 REAL DATA

        // 🔥 Assign rank
        int rank = 1;
        for (LeaderboardDTO dto : list) {
            dto.setRank(rank++);
        }

        return list;
    }
}