package com.curcus.lms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curcus.lms.service.InstructorStatisticService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statisticInstructor")
public class InstructorStatisticController {
    private final InstructorStatisticService instructorStatisticService;

}
