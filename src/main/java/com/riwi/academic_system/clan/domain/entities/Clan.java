package com.riwi.codeup.clan.domain.entities;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Clan {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long teamLeaderId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxCoders;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}