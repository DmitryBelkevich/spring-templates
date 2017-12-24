package com.hard.dao.impl;

import com.hard.dao.EntityDao;
import com.hard.models.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;

@Repository
public class EntityDaoImpl implements EntityDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Collection<Entity> getAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(Entity.class);

        Root<Entity> root = criteriaQuery.from(Entity.class);

        criteriaQuery.select(root);

        Query<Entity> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public Entity getById(long id) {
        Session session = sessionFactory.getCurrentSession();

        Entity e = session.get(Entity.class, id);

        return e;
    }

    @Override
    public void add(Entity entity) {
        Session session = sessionFactory.getCurrentSession();

        session.save(entity);
    }

    @Override
    public void add(Collection<Entity> entities) {
        Session session = sessionFactory.getCurrentSession();

        for (Entity entity : entities)
            session.save(entity);
    }

    @Override
    public void update(long id, Entity entity) {
        Session session = sessionFactory.getCurrentSession();

        Entity e = session.byId(Entity.class).load(id);
//        e.setId(entity.getId());
        e.setName(entity.getName());

        session.flush();
    }

    @Override
    public void update(Collection<Entity> entities) {
        Session session = sessionFactory.getCurrentSession();

        for (Entity entity : entities) {
            Entity e = session.byId(Entity.class).load(entity.getId());
//            e.setId(entity.getId());
            e.setName(entity.getName());

            session.flush();
        }
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();

        Entity e = session.byId(Entity.class).load(id);

        session.delete(e);
    }

    @Override
    public void clear() {
        Session session = sessionFactory.getCurrentSession();

        Query<Entity> query = session.createQuery("DELETE FROM " + Entity.class.getName());

        query.executeUpdate();
    }
}
