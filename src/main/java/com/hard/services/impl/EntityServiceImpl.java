package com.hard.services.impl;

import com.hard.dao.EntityDao;
import com.hard.models.Entity;
import com.hard.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class EntityServiceImpl implements EntityService {
    @Autowired
    private EntityDao entityDao;

    @Override
    public Collection<Entity> getAll() {
        return entityDao.getAll();
    }

    @Override
    public Entity getById(long id) {
        return entityDao.getById(id);
    }

    @Override
    public void add(Entity entity) {
        entityDao.add(entity);
    }

    @Override
    public void add(Collection<Entity> entities) {
        entityDao.add(entities);
    }

    @Override
    public void update(long id, Entity entity) {
        entityDao.update(id, entity);
    }

    @Override
    public void update(Collection<Entity> entities) {
        entityDao.update(entities);
    }

    @Override
    public void delete(long id) {
        entityDao.delete(id);
    }

    @Override
    public void clear() {
        entityDao.clear();
    }
}
