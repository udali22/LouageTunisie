package com.example.louagetunisie.controller;

import com.example.louagetunisie.service.LogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/logs")
public class LogController {
    private LogService logService;
    public LogController(LogService logService) {
        this.logService = logService;
    }
}
