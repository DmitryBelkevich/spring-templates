package com.hard.dao;

import com.hard.models.Entity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public interface EntityDao {
    Collection<Entity> getAll();

    Entity getById(long id);

    void add(Entity entity);

    void add(Collection<Entity> entities);

    void update(long id, Entity entity);

    void update(Collection<Entity> entities);

    void delete(long id);

    void clear();
}
