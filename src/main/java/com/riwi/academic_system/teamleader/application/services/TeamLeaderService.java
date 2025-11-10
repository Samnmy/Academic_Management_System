package com.riwi.codeup.teamleader.application.services;

import com.riwi.codeup.teamleader.domain.entities.TeamLeader;
import com.riwi.codeup.teamleader.infrastructure.repositories.TeamLeaderRepository;
import com.riwi.codeup.teamleader.web.dtos.TeamLeaderRequest;
import com.riwi.codeup.teamleader.web.dtos.TeamLeaderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamLeaderService {

    private final TeamLeaderRepository teamLeaderRepository;

    public TeamLeaderResponse create(TeamLeaderRequest request) {
        if (teamLeaderRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("TeamLeader code already exists: " + request.getCode());
        }
        if (teamLeaderRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        TeamLeader teamLeader = TeamLeader.builder()
                .code(request.getCode())
                .identification(request.getIdentification())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .specialization(request.getSpecialization())
                .techStack(request.getTechStack())
                .hireDate(request.getHireDate())
                .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        TeamLeader savedTeamLeader = teamLeaderRepository.save(teamLeader);
        return mapToResponse(savedTeamLeader);
    }

    public List<TeamLeaderResponse> findAll() {
        return teamLeaderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TeamLeaderResponse findById(Long id) {
        TeamLeader teamLeader = teamLeaderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TeamLeader not found with id: " + id));
        return mapToResponse(teamLeader);
    }

    public TeamLeaderResponse update(Long id, TeamLeaderRequest request) {
        TeamLeader existingTeamLeader = teamLeaderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TeamLeader not found with id: " + id));

        if (!existingTeamLeader.getCode().equals(request.getCode()) &&
                teamLeaderRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("TeamLeader code already exists: " + request.getCode());
        }

        if (!existingTeamLeader.getEmail().equals(request.getEmail()) &&
                teamLeaderRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        existingTeamLeader.setCode(request.getCode());
        existingTeamLeader.setIdentification(request.getIdentification());
        existingTeamLeader.setFirstName(request.getFirstName());
        existingTeamLeader.setLastName(request.getLastName());
        existingTeamLeader.setEmail(request.getEmail());
        existingTeamLeader.setPhone(request.getPhone());
        existingTeamLeader.setSpecialization(request.getSpecialization());
        existingTeamLeader.setTechStack(request.getTechStack());
        existingTeamLeader.setHireDate(request.getHireDate());
        existingTeamLeader.setStatus(request.getStatus());
        existingTeamLeader.setUpdatedAt(LocalDateTime.now());

        TeamLeader updatedTeamLeader = teamLeaderRepository.save(existingTeamLeader);
        return mapToResponse(updatedTeamLeader);
    }

    public void delete(Long id) {
        if (!teamLeaderRepository.findById(id).isPresent()) {
            throw new RuntimeException("TeamLeader not found with id: " + id);
        }
        teamLeaderRepository.deleteById(id);
    }

    private TeamLeaderResponse mapToResponse(TeamLeader teamLeader) {
        return TeamLeaderResponse.builder()
                .id(teamLeader.getId())
                .code(teamLeader.getCode())
                .identification(teamLeader.getIdentification())
                .firstName(teamLeader.getFirstName())
                .lastName(teamLeader.getLastName())
                .email(teamLeader.getEmail())
                .phone(teamLeader.getPhone())
                .specialization(teamLeader.getSpecialization())
                .techStack(teamLeader.getTechStack())
                .hireDate(teamLeader.getHireDate())
                .status(teamLeader.getStatus())
                .createdAt(teamLeader.getCreatedAt())
       package com.riwi.codeup.teamleader.web.controllers;

import com.riwi.codeup.teamleader.application.services.TeamLeaderService;
import com.riwi.codeup.teamleader.web.dtos.TeamLeaderRequest;
import com.riwi.codeup.teamleader.web.dtos.TeamLeaderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team-leaders")
@RequiredArgsConstructor
@Tag(name = "Team Leaders", description = "Operaciones CRUD para gestión de team leaders")
public class TeamLeaderController {

    private final TeamLeaderService teamLeaderService;

    @Operation(summary = "Crear un nuevo team leader", description = "Registra un nuevo team leader en el sistema")
    @PostMapping
    public ResponseEntity<TeamLeaderResponse> create(@RequestBody TeamLeaderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamLeaderService.create(request));
    }

    @Operation(summary = "Obtener todos los team leaders", description = "Retorna la lista completa de team leaders")
    @GetMapping
    public ResponseEntity<List<TeamLeaderResponse>> findAll() {
        return ResponseEntity.ok(teamLeaderService.findAll());
    }

    @Operation(summary = "Obtener team leader por ID", description = "Busca un team leader específico por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TeamLeaderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(teamLeaderService.findById(id));
    }

    @Operation(summary = "Actualizar team leader existente", description = "Actualiza la información de un team leader")
    @PutMapping("/{id}")
    public ResponseEntity<TeamLeaderResponse> update(
            @PathVariable Long id,
            @RequestBody TeamLeaderRequest request) {
        return ResponseEntity.ok(teamLeaderService.update(id, request));
    }

    @Operation(summary = "Eliminar team leader por ID", description = "Elimina un team leader del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teamLeaderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}         .updatedAt(teamLeader.getUpdatedAt())
                .build();
    }
}