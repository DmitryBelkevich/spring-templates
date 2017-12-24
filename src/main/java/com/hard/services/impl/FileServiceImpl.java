package com.hard.services.impl;

import com.hard.models.FileEntity;
import com.hard.services.FileEntityService;
import com.hard.services.FileManagerService;
import com.hard.services.FileService;
import com.hard.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileEntityService fileEntityService;

    @Autowired
    private FileManagerService fileManagerService;

    @Override
    public Collection<File> getAll() {
        Collection<File> files = new ArrayList<>();

        Collection<FileEntity> fileEntities = fileEntityService.getAll();

        for (FileEntity fileEntity : fileEntities) {
            String filePath = fileEntity.getFilePath();
            files.add(new File(filePath));
        }

        return files;
    }

    @Override
    public File getById(long id) {
        FileEntity fileEntity = fileEntityService.getById(id);

        if (fileEntity == null)
            return null;

        File file = fileManagerService.getFile(Constants.FILE_STORAGE + fileEntity.getFilePath());
        if (file.exists())
            return file;

        return null;
    }

    @Override
    public File getByFilePath(String filePath) {
        FileEntity fileEntity = fileEntityService.getByFilePath(filePath);

        if (fileEntity == null)
            return null;

        File file = fileManagerService.getFile(Constants.FILE_STORAGE + fileEntity.getFilePath());
        if (file.exists())
            return file;

        return null;
    }

    @Override
    public InputStream getFileInputStreamById(long id) {
        FileEntity fileEntity = fileEntityService.getById(id);

        if (fileEntity == null)
            return null;

        return fileManagerService.getFileInputStream(Constants.FILE_STORAGE + fileEntity.getFilePath());
    }

    @Override
    public InputStream getFileInputStreamByFilePath(String filePath) {
        FileEntity fileEntity = fileEntityService.getByFilePath(filePath);

        if (fileEntity == null)
            return null;

        return fileManagerService.getFileInputStream(Constants.FILE_STORAGE + fileEntity.getFilePath());
    }

    @Override
    public void add(String filePath, String content) {
        fileManagerService.write(Constants.FILE_STORAGE + filePath, content);

        if (new File(filePath).exists())
            fileEntityService.add(new FileEntity(filePath));
    }

    @Override
    public void add(String filePath, byte[] bytes) {
        fileManagerService.write(Constants.FILE_STORAGE + filePath, bytes);

        if (new File(filePath).exists())
            fileEntityService.add(new FileEntity(filePath));
    }

    @Override
    public void update(long id, String content) {
        FileEntity fileEntity = fileEntityService.getById(id);

        fileManagerService.delete(fileEntity.getFilePath());

        fileManagerService.write(fileEntity.getFilePath(), content);
    }

    @Override
    public void delete(long id) {
        FileEntity fileEntity = fileEntityService.getById(id);

        fileManagerService.delete(Constants.FILE_STORAGE + fileEntity.getFilePath());

        fileEntityService.delete(id);
    }

    @Override
    public void clear() {
        fileManagerService.clear(new File(Constants.FILE_STORAGE));

        fileEntityService.clear();
    }
}
