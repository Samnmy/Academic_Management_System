package com.riwi.codeup.clan.web.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClanRequest {
    private String code;
    private String name;
    private String description;
    private Long teamLeaderId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxCoders;
    private String status;
}