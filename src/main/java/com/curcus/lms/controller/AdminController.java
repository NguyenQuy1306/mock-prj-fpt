package com.curcus.lms.controller;



import com.curcus.lms.model.entity.Admin;
import com.curcus.lms.model.request.CategoryRequest;
import com.curcus.lms.model.response.AdminResponse;
import com.curcus.lms.model.response.ApiResponse;
import com.curcus.lms.model.response.CategoryResponse;
import com.curcus.lms.model.response.ResponseCode;
import com.curcus.lms.service.AdminService;
import com.curcus.lms.service.CategorySevice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CategorySevice categorySevice;

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
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            com.curcus.lms.model.response.ApiResponse<AdminResponse> apiResponse = new com.curcus.lms.model.response.ApiResponse<>();
            apiResponse.error(error);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse<CategoryResponse>> addCategory(
            @Valid @RequestBody CategoryRequest categoryRequest,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) throw new Exception("Request không hợp lệ");
            CategoryResponse categoryResponse = categorySevice.createCategory(categoryRequest);
            ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>();
            apiResponse.ok(categoryResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception ex) {
            ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>();
            apiResponse.error(ResponseCode.getError(23));
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}