package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public Map<String, String> greet(@RequestParam(defaultValue = "Guest") String name) {
        return Map.of(
                "message", "Hello, " + name + "!",
                "status", "success"
        );
    }
}
