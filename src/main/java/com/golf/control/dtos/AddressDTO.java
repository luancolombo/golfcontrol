package com.golf.control.dtos;

import com.golf.control.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AddressDTO {
    private Long id;
    private String streetName;
    private String postalCode;
    private String country;
    public AddressDTO() {
    }
}
