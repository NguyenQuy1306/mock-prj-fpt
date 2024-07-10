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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.exception.ValidationException;
import com.curcus.lms.model.request.InstructorRequest;
import com.curcus.lms.model.request.InstructorUpdateRequest;
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
            if (instructorService.findAll().size()==0) throw new NotFoundException("Instructor not found.");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }

    }

    @GetMapping(value = {"/name/{name}"})
    public ResponseEntity<ApiResponse<List<InstructorResponse>>> getInstructorsByName(@PathVariable String name){
        try {
            ApiResponse<List<InstructorResponse>> apiResponse = new ApiResponse<>();
            apiResponse.ok(instructorService.findByName(name));
            if (instructorService.findByName(name).size()==0) throw new NotFoundException("Instructor not found.");
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
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
                throw new NotFoundException("Instructor not found.");
            }
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }

    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<InstructorResponse>> createInstructor(@Valid @RequestBody InstructorRequest instructorRequest, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                Map<String, String> errors= new HashMap<>();
    
                bindingResult.getFieldErrors().forEach(
                        error -> errors.put(error.getField(), error.getDefaultMessage())
                );
                throw new ValidationException(errors);
            }
            InstructorResponse instructorResponse = instructorService.createInstructor(instructorRequest);
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(instructorResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED );
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }

    }

    @PutMapping(value = {"/{id}"})
    public ResponseEntity<ApiResponse<InstructorResponse>> updateInstructor(@PathVariable Long id, @Valid @RequestBody InstructorUpdateRequest instructorUpdateRequest, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()){
                Map<String, String> errors= new HashMap<>();
    
                bindingResult.getFieldErrors().forEach(
                        error -> errors.put(error.getField(), error.getDefaultMessage())
                );
                throw new ValidationException(errors);
            }
            InstructorResponse instructorResponse = instructorService.updateInstructor(instructorUpdateRequest, id);
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(instructorResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @PutMapping(value = {"/{id}/updatepassword"})
    public ResponseEntity<ApiResponse<InstructorResponse>> updateInstructorPassword(@PathVariable Long id, @Valid @RequestParam String password) {
        try {
            InstructorResponse instructorResponse = instructorService.updateInstructorPassword(id, password);
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(instructorResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @PutMapping(value = {"/{id}/recoverpassword"})
    public ResponseEntity<ApiResponse<InstructorResponse>> recoverInstructorPassword(@PathVariable Long id, @Valid @RequestParam String password) {
        try {
            InstructorResponse instructorResponse = instructorService.recoverInstructorPassword(id, password);
            ApiResponse<InstructorResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(instructorResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    @DeleteMapping(value = {"/{id}"})
    public ResponseEntity<ApiResponse<Void>> deleteInstructor(@PathVariable Long id) {
        try {
            instructorService.deleteInstructor(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>();
            apiResponse.ok();
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (NotFoundException ex) {
            throw ex;
        } catch (ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ApplicationException();
        }

    }
}
