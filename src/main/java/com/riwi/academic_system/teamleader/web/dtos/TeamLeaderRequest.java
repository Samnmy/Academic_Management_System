package com.riwi.codeup.teamleader.web.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamLeaderRequest {
    private String code;
    private String identification;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
    private String techStack;
    private LocalDate hireDate;
    private String status;
}