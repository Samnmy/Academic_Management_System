package com.riwi.codeup.coder.web.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoderRequest {
    private String code;
    private String identification;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String address;
    private LocalDate enrollmentDate;
    private String status;
}