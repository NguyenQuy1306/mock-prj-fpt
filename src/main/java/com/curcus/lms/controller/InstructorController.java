package com.curcus.lms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.request.InstructorRequest;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.InstructorResponse;
import com.curcus.lms.service.InstructorService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @GetMapping(value = {""})
    public ResponseEntity<ApiResponse<List<InstructorResponse>>> getAllInstructors(){
        try {
            ApiResponse<List<InstructorResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(instructorService.findAll());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while processing the request");
            ApiResponse<List<InstructorResponse>> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = {"/name/{name}"})
    public ResponseEntity<ApiResponse<List<InstructorResponse>>> getInstructorsByName(@PathVariable String name){
        try {
            ApiResponse<List<InstructorResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(instructorService.findByName(name));
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while processing the request");
            ApiResponse<List<InstructorResponse>> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = {"/id/{id}"})
    public ResponseEntity<ApiResponse<InstructorResponse>> findById(@PathVariable Long id){
        try {
            Optional<InstructorResponse> instructorResponse = instructorService.findById(id);
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            if (instructorResponse.isPresent()) {
                apiResponse.ok(instructorResponse.get());
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Instructor not found");
                apiResponse.error(error);
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while processing the request");
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<InstructorResponse>> createInstructor(@Valid @RequestBody InstructorRequest instructorRequest, BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()) throw new Exception("Request không hợp lệ");
            InstructorResponse instructorResponse = instructorService.saveInstructor(instructorRequest);
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(instructorResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED );
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while saving the instructor");
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {"/{id}"})
    public ResponseEntity<ApiResponse<InstructorResponse>> updateInstructor(@PathVariable Long id, @Valid @RequestBody InstructorRequest instructorRequest, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) throw new Exception("Request không hợp lệ");
            InstructorResponse instructorResponse = instructorService.newUpdateInstructor(instructorRequest, id);
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(instructorResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while updating the instructor");
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<ApiResponse<Void>> deleteInstructor(@PathVariable Long id) {
        try {
            instructorService.deleteInstructor(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.ok(null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "An error occurred while deleting the instructor");
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
