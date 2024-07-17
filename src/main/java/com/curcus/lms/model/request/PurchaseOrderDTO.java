package com.curcus.lms.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
public class PurchaseOrderDTO {
    @NotNull(message = "Price is mandatory")
    long totalPrice;
    @NotNull(message = "idCourses is mandatory")
    Long[] idCourses;
}
