package com.ranjitha.code_execution_engine.controller;

import com.ranjitha.code_execution_engine.entity.Problem;
import com.ranjitha.code_execution_engine.repository.ProblemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
@CrossOrigin
public class ProblemController {

    private final ProblemRepository repo;

    public ProblemController(ProblemRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Problem> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Problem getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    @PostMapping
    public Problem add(@RequestBody Problem p) {
        return repo.save(p);
    }

    @PutMapping("/{id}")
    public Problem update(@PathVariable Long id, @RequestBody Problem p) {
        p.setId(id);
        return repo.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}