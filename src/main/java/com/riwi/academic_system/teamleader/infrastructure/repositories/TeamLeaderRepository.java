package com.riwi.codeup.teamleader.infrastructure.repositories;

import com.riwi.codeup.teamleader.domain.entities.TeamLeader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TeamLeaderRepository {

    private final List<TeamLeader> teamLeaders = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public TeamLeader save(TeamLeader teamLeader) {
        if (teamLeader.getId() == null) {
            teamLeader.setId(idCounter.getAndIncrement());
            teamLeader.setCreatedAt(java.time.LocalDateTime.now());
        }
        teamLeader.setUpdatedAt(java.time.LocalDateTime.now());

        teamLeaders.removeIf(tl -> tl.getId().equals(teamLeader.getId()));
        teamLeaders.add(teamLeader);

        return teamLeader;
    }

    public List<TeamLeader> findAll() {
        return new ArrayList<>(teamLeaders);
    }

    public Optional<TeamLeader> findById(Long id) {
        return teamLeaders.stream()
                .filter(teamLeader -> teamLeader.getId().equals(id))
                .findFirst();
    }

    public Optional<TeamLeader> findByCode(String code) {
        return teamLeaders.stream()
                .filter(teamLeader -> teamLeader.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    public Optional<TeamLeader> findByEmail(String email) {
        return teamLeaders.stream()
                .filter(teamLeader -> teamLeader.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean existsByCode(String code) {
        return teamLeaders.stream()
                .anyMatch(teamLeader -> teamLeader.getCode().equalsIgnoreCase(code));
    }

    public boolean existsByEmail(String email) {
        return teamLeaders.stream()
                .anyMatch(teamLeader -> teamLeader.getEmail().equalsIgnoreCase(email));
    }

    public void deleteById(Long id) {
        teamLeaders.removeIf(teamLeader -> teamLeader.getId().equals(id));
    }
}