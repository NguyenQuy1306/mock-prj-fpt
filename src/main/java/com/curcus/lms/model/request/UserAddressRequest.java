package com.curcus.lms.model.request;

import com.google.firebase.database.annotations.NotNull;
import lombok.Getter;

import lombok.Setter;

@Setter
@Getter
public class UserAddressRequest {
    @NotNull ("User Address cannot be null")
    private String userAddress;
    @NotNull ("User City cannot be null")
    private String userCity;
    @NotNull ("User Country cannot be null")
    private String userCountry;
    @NotNull ("User Postal Code cannot be null")
    private Long userPostalCode;
}
