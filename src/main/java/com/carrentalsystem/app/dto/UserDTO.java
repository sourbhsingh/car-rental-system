package com.carrentalsystem.app.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data //
public class UserDTO {
    private String name;
    private String email;
    private String phone;
    private String password;
}
