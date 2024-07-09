package com.curcus.lms.controller;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.curcus.lms.model.dto.InstructorDTO;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.request.InstructorCreateRequest;
import com.curcus.lms.service.InstructorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/api/instructor")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;
    
    @PostMapping
    public ResponseEntity<InstructorDTO> createInstructor(@RequestBody InstructorCreateRequest request) {
        return new ResponseEntity<>(instructorService.createInstructor(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructor(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.getInstructor(id));
    }

    @GetMapping
    public ResponseEntity<List<InstructorDTO>> getAllInstructors() {
        return ResponseEntity.ok(instructorService.getAllInstructors());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(@PathVariable Long id, @RequestBody InstructorCreateRequest request) {
        return ResponseEntity.ok(instructorService.updateInstructor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }
    
}
