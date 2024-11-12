package com.onlybuns.isa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome";
    }

    // Dodajte mape za svaku od stranica
    @GetMapping("/admin/posts")
    public String viewPosts() {
        return "adminPosts";
    }

    @GetMapping("/admin/trends")
    public String viewTrends() {
        return "adminTrends";
    }

    @GetMapping("/admin/analytics")
    public String viewAnalytics() {
        return "adminAnalytics";
    }

    @GetMapping("/admin/users")
    public String viewUsers() {
        return "adminUsers";
    }
}