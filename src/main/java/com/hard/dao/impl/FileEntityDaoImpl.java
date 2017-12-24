package com.hard.dao.impl;

import com.hard.dao.FileEntityDao;
import com.hard.models.FileEntity;
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
public class FileEntityDaoImpl implements FileEntityDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Collection<FileEntity> getAll() {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<FileEntity> criteriaQuery = criteriaBuilder.createQuery(FileEntity.class);

        Root<FileEntity> root = criteriaQuery.from(FileEntity.class);

        criteriaQuery.select(root);

        Query<FileEntity> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public FileEntity getById(long id) {
        Session session = sessionFactory.getCurrentSession();

        FileEntity fe = session.get(FileEntity.class, id);

        return fe;
    }

    @Override
    public FileEntity getByFilePath(String filePath) {
        // TODO

        return null;
    }

    @Override
    public void add(FileEntity fileEntity) {
        Session session = sessionFactory.getCurrentSession();

        session.save(fileEntity);
    }

    @Override
    public void add(Collection<FileEntity> fileEntities) {
        Session session = sessionFactory.getCurrentSession();

        for (FileEntity fileEntity : fileEntities)
            session.save(fileEntity);
    }

    @Override
    public void update(long id, FileEntity fileEntity) {
        Session session = sessionFactory.getCurrentSession();

        FileEntity fe = session.byId(FileEntity.class).load(id);
        fe.setFilePath(fileEntity.getFilePath());

        session.flush();
    }

    @Override
    public void update(Collection<FileEntity> fileEntities) {
        Session session = sessionFactory.getCurrentSession();

        for (FileEntity fileEntity : fileEntities) {
            FileEntity fe = session.byId(FileEntity.class).load(fileEntity.getId());
            fe.setFilePath(fileEntity.getFilePath());

            session.flush();
        }
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();

        FileEntity fe = session.byId(FileEntity.class).load(id);

        session.delete(fe);
    }

    @Override
    public void delete(String filePath) {
        // TODO
    }

    @Override
    public void clear() {
        Session session = sessionFactory.getCurrentSession();

        Query<FileEntity> query = session.createQuery("DELETE FROM " + FileEntity.class.getName());

        query.executeUpdate();
    }
}
