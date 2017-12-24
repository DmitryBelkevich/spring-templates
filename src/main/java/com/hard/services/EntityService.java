package com.hard.services;

import com.hard.models.Entity;

import java.util.Collection;

public interface EntityService {
    Collection<Entity> getAll();

    Entity getById(long id);

    void add(Entity entity);

    void add(Collection<Entity> entities);

    void update(long id, Entity entity);

    void update(Collection<Entity> entities);

    void delete(long id);

    void clear();
}
