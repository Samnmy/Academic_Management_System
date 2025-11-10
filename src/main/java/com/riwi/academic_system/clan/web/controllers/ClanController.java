package com.riwi.codeup.clan.web.controllers;

import com.riwi.codeup.clan.application.services.ClanService;
import com.riwi.codeup.clan.web.dtos.ClanRequest;
import com.riwi.codeup.clan.web.dtos.ClanResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clans")
@RequiredArgsConstructor
@Tag(name = "Clans", description = "Operaciones CRUD para gestión de clanes")
public class ClanController {

    private final ClanService clanService;

    @Operation(summary = "Crear un nuevo clan", description = "Registra un nuevo clan en el sistema")
    @PostMapping
    public ResponseEntity<ClanResponse> create(@RequestBody ClanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clanService.create(request));
    }

    @Operation(summary = "Obtener todos los clanes", description = "Retorna la lista completa de clanes")
    @GetMapping
    public ResponseEntity<List<ClanResponse>> findAll() {
        return ResponseEntity.ok(clanService.findAll());
    }

    @Operation(summary = "Obtener clan por ID", description = "Busca un clan específico por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClanResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clanService.findById(id));
    }

    @Operation(summary = "Actualizar clan existente", description = "Actualiza la información de un clan")
    @PutMapping("/{id}")
    public ResponseEntity<ClanResponse> update(
            @PathVariable Long id,
            @RequestBody ClanRequest request) {
        return ResponseEntity.ok(clanService.update(id, request));
    }

    @Operation(summary = "Eliminar clan por ID", description = "Elimina un clan del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener clanes por team leader", description = "Busca clanes asignados a un team leader específico")
    @GetMapping("/team-leader/{teamLeaderId}")
    public ResponseEntity<List<ClanResponse>> findByTeamLeaderId(@PathVariable Long teamLeaderId) {
        return ResponseEntity.ok(clanService.findByTeamLeaderId(teamLeaderId));
    }
}