package com.curcus.lms.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import lombok.Setter;

@Setter
@Getter
public class UserAddressRequest {
    @NotNull(message = "User Address cannot be null")
    private String userAddress;
    @NotNull (message =  "User City cannot be null")
    private String userCity;
    @NotNull (message =  "User Country cannot be null")
    private String userCountry;
    @NotNull (message = "User Postal Code cannot be null")
    private Long userPostalCode;
}
