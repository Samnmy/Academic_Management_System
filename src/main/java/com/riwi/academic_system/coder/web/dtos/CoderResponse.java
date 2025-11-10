package com.riwi.codeup.coder.web.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoderResponse {
    private Long id;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}