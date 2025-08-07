package com.example.louagetunisie.controller;

import com.example.louagetunisie.service.ReclamationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reclamations")
public class ReclamationController {
    private ReclamationService reclamationService;
    public ReclamationController(ReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }
}
