package com.riwi.codeup.teamleader.web.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamLeaderResponse {
    private Long id;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}