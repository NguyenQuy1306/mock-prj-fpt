package com.curcus.lms.model.response;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import lombok.Setter;

@Setter
@Getter
public class UserAddressResponse {
    @NotNull(message = "User Id cannot be null")
    private String userId;
    @NotNull (message =  "User Address cannot be null")
    private String userAddress;
    @NotNull (message =  "User City cannot be null")
    private String userCity;
    @NotNull (message =  "User Country cannot be null")
    private String userCountry;
    @NotNull (message = "User Postal Code cannot be null")
    private Long userPostalCode;
}
