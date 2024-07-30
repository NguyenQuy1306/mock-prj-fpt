package com.curcus.lms.model.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InstructorUpdateRequest {
    @NotBlank(message = "Tên bị thiếu")
    private String name;
    @NotBlank(message = "Mật khẩu bị thiếu")
    private String firstName;
    private String lastName;
    @NotBlank(message = "Số điện thoại bị thiếu")
    private String phoneNumber;

    private MultipartFile avt;

}
