package com.example.louagetunisie.controller;

import com.example.louagetunisie.repository.AdminRepository;
import com.example.louagetunisie.service.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController  {
    private AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
}
