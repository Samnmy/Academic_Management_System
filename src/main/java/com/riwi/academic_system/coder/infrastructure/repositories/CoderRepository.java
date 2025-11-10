package com.riwi.codeup.coder.infrastructure.repositories;

import com.riwi.codeup.coder.domain.entities.Coder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CoderRepository {

    private final List<Coder> coders = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Coder save(Coder coder) {
        if (coder.getId() == null) {
            coder.setId(idCounter.getAndIncrement());
            coder.setCreatedAt(java.time.LocalDateTime.now());
        }
        coder.setUpdatedAt(java.time.LocalDateTime.now());

        // Remove if exists and add new
        coders.removeIf(c -> c.getId().equals(coder.getId()));
        coders.add(coder);

        return coder;
    }

    public List<Coder> findAll() {
        return new ArrayList<>(coders);
    }

    public Optional<Coder> findById(Long id) {
        return coders.stream()
                .filter(coder -> coder.getId().equals(id))
                .findFirst();
    }

    public Optional<Coder> findByCode(String code) {
        return coders.stream()
                .filter(coder -> coder.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    public Optional<Coder> findByEmail(String email) {
        return coders.stream()
                .filter(coder -> coder.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean existsByCode(String code) {
        return coders.stream()
                .anyMatch(coder -> coder.getCode().equalsIgnoreCase(code));
    }

    public boolean existsByEmail(String email) {
        return coders.stream()
                .anyMatch(coder -> coder.getEmail().equalsIgnoreCase(email));
    }

    public boolean existsByIdentification(String identification) {
        return coders.stream()
                .anyMatch(coder -> coder.getIdentification().equals(identification));
    }

    public void deleteById(Long id) {
        coders.removeIf(coder -> coder.getId().equals(id));
    }
}