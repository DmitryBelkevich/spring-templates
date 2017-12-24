package com.hard.services;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

public interface FileManagerService {
    String read(String filePath);

    void write(String filePath, String content);

    void write(String filePath, byte[] bytes);

    File getFile(String filePath);

    InputStream getFileInputStream(String filePath);

    Collection<File> scanFolder(File folder);

    Collection<File> scanFolderTree(File folder);

    void create(String filePath);

    void move(String from, String to);

    void copy(String from, String to);

    void rename(String from, String to);

    void delete(String filePath);

    void clear(File folder);
}
