package com.hard.services.impl;

import com.hard.dao.FileEntityDao;
import com.hard.models.FileEntity;
import com.hard.services.FileEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class FileEntityServiceImpl implements FileEntityService {
    @Autowired
    private FileEntityDao fileEntityDao;

    @Override
    public Collection<FileEntity> getAll() {
        return fileEntityDao.getAll();
    }

    @Override
    public FileEntity getById(long id) {
        return fileEntityDao.getById(id);
    }

    @Override
    public FileEntity getByFilePath(String filePath) {
        return fileEntityDao.getByFilePath(filePath);
    }

    @Override
    public void add(FileEntity fileEntity) {
        fileEntityDao.add(fileEntity);
    }

    @Override
    public void add(Collection<FileEntity> fileEntities) {
        fileEntityDao.add(fileEntities);
    }

    @Override
    public void update(long id, FileEntity fileEntity) {
        fileEntityDao.update(id, fileEntity);
    }

    @Override
    public void update(Collection<FileEntity> fileEntities) {
        fileEntityDao.update(fileEntities);
    }

    @Override
    public void delete(long id) {
        fileEntityDao.delete(id);
    }

    @Override
    public void delete(String filePath) {
        fileEntityDao.delete(filePath);
    }

    @Override
    public void clear() {
        fileEntityDao.clear();
    }
}
