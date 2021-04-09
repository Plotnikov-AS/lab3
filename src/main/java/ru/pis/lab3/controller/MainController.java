package ru.pis.lab3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pis.lab3.service.CourseService;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CourseService courseService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/calc")
    public String calculateMiddle() {
        courseService.calculateMidCourseVal();
        return "redirect:/";
    }
}
