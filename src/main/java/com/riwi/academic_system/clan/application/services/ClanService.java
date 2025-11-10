package com.riwi.codeup.clan.application.services;

import com.riwi.codeup.clan.domain.entities.Clan;
import com.riwi.codeup.clan.infrastructure.repositories.ClanRepository;
import com.riwi.codeup.clan.web.dtos.ClanRequest;
import com.riwi.codeup.clan.web.dtos.ClanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClanService {

    private final ClanRepository clanRepository;

    public ClanResponse create(ClanRequest request) {
        if (clanRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Clan code already exists: " + request.getCode());
        }

        Clan clan = Clan.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .teamLeaderId(request.getTeamLeaderId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .maxCoders(request.getMaxCoders() != null ? request.getMaxCoders() : 20)
                .status(request.getStatus() != null ? request.getStatus() : "FORMING")
                .createdAt(LocalDateTime.now())
                .build();

        Clan savedClan = clanRepository.save(clan);
        return mapToResponse(savedClan);
    }

    public List<ClanResponse> findAll() {
        return clanRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ClanResponse findById(Long id) {
        Clan clan = clanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan not found with id: " + id));
        return mapToResponse(clan);
    }

    public ClanResponse update(Long id, ClanRequest request) {
        Clan existingClan = clanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clan not found with id: " + id));

        if (!existingClan.getCode().equals(request.getCode()) &&
                clanRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Clan code already exists: " + request.getCode());
        }

        existingClan.setCode(request.getCode());
        existingClan.setName(request.getName());
        existingClan.setDescription(request.getDescription());
        existingClan.setTeamLeaderId(request.getTeamLeaderId());
        existingClan.setStartDate(request.getStartDate());
        existingClan.setEndDate(request.getEndDate());
        existingClan.setMaxCoders(request.getMaxCoders());
        existingClan.setStatus(request.getStatus());
        existingClan.setUpdatedAt(LocalDateTime.now());

        Clan updatedClan = clanRepository.save(existingClan);
        return mapToResponse(updatedClan);
    }

    public void delete(Long id) {
        if (!clanRepository.findById(id).isPresent()) {
            throw new RuntimeException("Clan not found with id: " + id);
        }
        clanRepository.deleteById(id);
    }

    public List<ClanResponse> findByTeamLeaderId(Long teamLeaderId) {
        return clanRepository.findByTeamLeaderId(teamLeaderId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ClanResponse mapToResponse(Clan clan) {
        return ClanResponse.builder()
                .id(clan.getId())
                .code(clan.getCode())
                .name(clan.getName())
                .description(clan.getDescription())
                .teamLeaderId(clan.getTeamLeaderId())
                .startDate(clan.getStartDate())
                .endDate(clan.getEndDate())
                .maxCoders(clan.getMaxCoders())
                .status(clan.getStatus())
                .createdAt(clan.getCreatedAt())
                .updatedAt(clan.getUpdatedAt())
                .build();
    }
}