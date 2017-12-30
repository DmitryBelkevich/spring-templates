package com.hard.services;

import com.hard.models.Entity;

import java.util.Collection;

public interface EntityService {
    Collection<Entity> getAll();

    Entity getById(long id);

    Entity getByName(String name);

    Entity save(Entity entity);

    void delete(long id);

    void deleteAll();
}
