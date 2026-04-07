package com.ranjitha.code_execution_engine.controller;

import com.ranjitha.code_execution_engine.dto.LeaderboardDTO;
import com.ranjitha.code_execution_engine.service.LeaderboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/api/leaderboard")
    public List<LeaderboardDTO> getLeaderboard() {
        return leaderboardService.getLeaderboard();
    }
}