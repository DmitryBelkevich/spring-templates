package com.hard.services;

import com.hard.models.FileEntity;

import java.util.Collection;

public interface FileEntityService {
    Collection<FileEntity> getAll();

    FileEntity getById(long id);

    FileEntity getByFilePath(String filePath);

    void add(FileEntity fileEntity);

    void add(Collection<FileEntity> fileEntities);

    void update(long id, FileEntity fileEntity);

    void update(Collection<FileEntity> fileEntities);

    void delete(long id);

    void delete(String filePath);

    void clear();
}
