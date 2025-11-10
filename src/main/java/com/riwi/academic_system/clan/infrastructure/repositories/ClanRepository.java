package com.riwi.codeup.clan.infrastructure.repositories;

import com.riwi.codeup.clan.domain.entities.Clan;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ClanRepository {

    private final List<Clan> clans = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Clan save(Clan clan) {
        if (clan.getId() == null) {
            clan.setId(idCounter.getAndIncrement());
            clan.setCreatedAt(java.time.LocalDateTime.now());
        }
        clan.setUpdatedAt(java.time.LocalDateTime.now());

        clans.removeIf(c -> c.getId().equals(clan.getId()));
        clans.add(clan);

        return clan;
    }

    public List<Clan> findAll() {
        return new ArrayList<>(clans);
    }

    public Optional<Clan> findById(Long id) {
        return clans.stream()
                .filter(clan -> clan.getId().equals(id))
                .findFirst();
    }

    public Optional<Clan> findByCode(String code) {
        return clans.stream()
                .filter(clan -> clan.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    public boolean existsByCode(String code) {
        return clans.stream()
                .anyMatch(clan -> clan.getCode().equalsIgnoreCase(code));
    }

    public List<Clan> findByTeamLeaderId(Long teamLeaderId) {
        return clans.stream()
                .filter(clan -> clan.getTeamLeaderId().equals(teamLeaderId))
                .collect(java.util.stream.Collectors.toList());
    }

    public void deleteById(Long id) {
        clans.removeIf(clan -> clan.getId().equals(id));
    }
}