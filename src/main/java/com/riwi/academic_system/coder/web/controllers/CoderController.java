package com.riwi.codeup.coder.web.controllers;

import com.riwi.codeup.coder.application.services.CoderService;
import com.riwi.codeup.coder.web.dtos.CoderRequest;
import com.riwi.codeup.coder.web.dtos.CoderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coders")
@RequiredArgsConstructor
@Tag(name = "Coders", description = "Operaciones CRUD para gestión de coders")
public class CoderController {

    private final CoderService coderService;

    @Operation(summary = "Crear un nuevo coder", description = "Registra un nuevo coder en el sistema")
    @PostMapping
    public ResponseEntity<CoderResponse> create(@RequestBody CoderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(coderService.create(request));
    }

    @Operation(summary = "Obtener todos los coders", description = "Retorna la lista completa de coders")
    @GetMapping
    public ResponseEntity<List<CoderResponse>> findAll() {
        return ResponseEntity.ok(coderService.findAll());
    }

    @Operation(summary = "Obtener coder por ID", description = "Busca un coder específico por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<CoderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(coderService.findById(id));
    }

    @Operation(summary = "Actualizar coder existente", description = "Actualiza la información de un coder")
    @PutMapping("/{id}")
    public ResponseEntity<CoderResponse> update(
            @PathVariable Long id,
            @RequestBody CoderRequest request) {
        return ResponseEntity.ok(coderService.update(id, request));
    }

    @Operation(summary = "Eliminar coder por ID", description = "Elimina un coder del sistema")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        coderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}