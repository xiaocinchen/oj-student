package com.offer.oj.student.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginQuery implements Serializable {

    @NotNull
    @Size(min = 6, max = 20, message = "The username should be between 6 and 20 characters!")
    private String username;

    @NotNull
    @Email(message = "Email format error!")
    private String email;

    @NotNull
    @Size(max = 12, message = "FirstName cannot exceed 12 characters!")
    private String firstName;

    @NotNull
    @Size(max = 12, message = "LastName cannot exceed 12 characters!")
    private String lastName;

    @NotNull
    private String gender;

    @NotNull
    @Size(min = 8, max = 20, message = "Password should be composed of 8 to 20 characters of numbers or English!")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Password should be numbers or English!")
    private String password;

    @Null
    private String role;

    @Serial
    private static final long serialVersionUID = 1L;


}
