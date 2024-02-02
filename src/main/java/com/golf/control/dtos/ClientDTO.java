package com.golf.control.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class ClientDTO {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String email;


    public ClientDTO() {
    }
}
