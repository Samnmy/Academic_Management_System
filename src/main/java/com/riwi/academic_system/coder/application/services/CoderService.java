package com.riwi.codeup.coder.application.services;

import com.riwi.codeup.coder.domain.entities.Coder;
import com.riwi.codeup.coder.infrastructure.repositories.CoderRepository;
import com.riwi.codeup.coder.web.dtos.CoderRequest;
import com.riwi.codeup.coder.web.dtos.CoderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoderService {

    private final CoderRepository coderRepository;

    public CoderResponse create(CoderRequest request) {
        // Validate unique constraints
        if (coderRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Coder code already exists: " + request.getCode());
        }
        if (coderRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        if (coderRepository.existsByIdentification(request.getIdentification())) {
            throw new RuntimeException("Identification already exists: " + request.getIdentification());
        }

        Coder coder = Coder.builder()
                .code(request.getCode())
                .identification(request.getIdentification())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .birthDate(request.getBirthDate())
                .address(request.getAddress())
                .enrollmentDate(request.getEnrollmentDate())
                .status(request.getStatus() != null ? request.getStatus() : "ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        Coder savedCoder = coderRepository.save(coder);
        return mapToResponse(savedCoder);
    }

    public List<CoderResponse> findAll() {
        return coderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CoderResponse findById(Long id) {
        Coder coder = coderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coder not found with id: " + id));
        return mapToResponse(coder);
    }

    public CoderResponse update(Long id, CoderRequest request) {
        Coder existingCoder = coderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coder not found with id: " + id));

        // Validate unique code if changed
        if (!existingCoder.getCode().equals(request.getCode()) &&
                coderRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Coder code already exists: " + request.getCode());
        }

        // Validate unique email if changed
        if (!existingCoder.getEmail().equals(request.getEmail()) &&
                coderRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        // Update fields
        existingCoder.setCode(request.getCode());
        existingCoder.setIdentification(request.getIdentification());
        existingCoder.setFirstName(request.getFirstName());
        existingCoder.setLastName(request.getLastName());
        existingCoder.setEmail(request.getEmail());
        existingCoder.setPhone(request.getPhone());
        existingCoder.setBirthDate(request.getBirthDate());
        existingCoder.setAddress(request.getAddress());
        existingCoder.setEnrollmentDate(request.getEnrollmentDate());
        existingCoder.setStatus(request.getStatus());
        existingCoder.setUpdatedAt(LocalDateTime.now());

        Coder updatedCoder = coderRepository.save(existingCoder);
        return mapToResponse(updatedCoder);
    }

    public void delete(Long id) {
        if (!coderRepository.findById(id).isPresent()) {
            throw new RuntimeException("Coder not found with id: " + id);
        }
        coderRepository.deleteById(id);
    }

    private CoderResponse mapToResponse(Coder coder) {
        return CoderResponse.builder()
                .id(coder.getId())
                .code(coder.getCode())
                .identification(coder.getIdentification())
                .firstName(coder.getFirstName())
                .lastName(coder.getLastName())
                .email(coder.getEmail())
                .phone(coder.getPhone())
                .birthDate(coder.getBirthDate())
                .address(coder.getAddress())
                .enrollmentDate(coder.getEnrollmentDate())
                .status(coder.getStatus())
                .createdAt(coder.getCreatedAt())
                .updatedAt(coder.getUpdatedAt())
                .build();
    }
}