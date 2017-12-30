package com.hard.services.impl;

import com.hard.models.Entity;
import com.hard.repositories.EntityRepository;
import com.hard.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityRepository entityRepository;

    @Override
    public Collection<Entity> getAll() {
        return entityRepository.findAll();
    }

    @Override
    public Entity getById(long id) {
        return entityRepository.findOne(id);
    }

    @Override
    public Entity getByName(String name) {
        return entityRepository.findByName(name);
    }

    @Override
    public Entity save(Entity entity) {
        return entityRepository.save(entity);
    }

    @Override
    public void delete(long id) {
        entityRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        entityRepository.deleteAll();
    }
}
