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
}