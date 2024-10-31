package com.cnpm.lms.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import com.cnpm.lms.exception.ApplicationException;
import com.cnpm.lms.exception.NotFoundException;
import com.cnpm.lms.exception.ValidationException;
// import com.cnpm.lms.model.request.CourseStatusRequest;
import com.cnpm.lms.model.response.*;
import com.cnpm.lms.service.AdminService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ROLE_ADMIN') and authentication.principal.getId() == #id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminResponse>> getAdminById(@PathVariable Long id) {
        try {
            Optional<AdminResponse> adminResponse = adminService.findById(id);
            ApiResponse<AdminResponse> apiResponse = new ApiResponse<>();
            if (adminResponse.isPresent()) {
                apiResponse.ok(adminResponse.get());
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Admin not found");
                apiResponse.error(error);
                return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new ApplicationException();
        }
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @PostMapping("/add-category")
    // public ResponseEntity<ApiResponse<CategoryResponse>> addCategory(
    // @Valid @RequestBody CategoryRequest categoryRequest,
    // BindingResult bindingResult) {

    // if (bindingResult.hasErrors()) {
    // throw new ValidationException(
    // bindingResult.getAllErrors().stream()
    // .collect(Collectors.toMap(
    // error -> ((FieldError) error).getField(),
    // error -> error.getDefaultMessage())));
    // }
    // CategoryResponse categoryResponse =
    // categorySevice.createCategory(categoryRequest);
    // ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>();
    // apiResponse.ok(categoryResponse);
    // return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    // // catch (ValidationException ex) {
    // // throw ex;
    // // }

    // }

}
