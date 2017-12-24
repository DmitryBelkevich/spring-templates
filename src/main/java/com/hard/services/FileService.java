package com.hard.services;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

public interface FileService {
    Collection<File> getAll();

    File getById(long id);

    File getByFilePath(String filePath);

    InputStream getFileInputStreamById(long id);

    InputStream getFileInputStreamByFilePath(String filePath);

    void add(String filePath, String content);

    void add(String filePath, byte[] bytes);

    void update(long id, String content);

    void delete(long id);

    void clear();
}
