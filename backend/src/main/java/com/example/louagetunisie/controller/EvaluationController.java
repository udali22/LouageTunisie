package com.example.louagetunisie.controller;

import com.example.louagetunisie.service.EvaluationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/evaluations")
public class EvaluationController {
    private EvaluationService evaluationService;
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }
}
