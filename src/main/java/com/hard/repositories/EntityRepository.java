package com.hard.repositories;

import com.hard.models.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository extends JpaRepository<Entity, Long> {
    Entity findByName(String name);
}
