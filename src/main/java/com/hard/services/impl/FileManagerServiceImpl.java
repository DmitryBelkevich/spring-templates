package com.hard.services.impl;

import com.hard.services.FileManagerService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class FileManagerServiceImpl implements FileManagerService {
    @Override
    public String read(String filePath) {
        StringBuilder content = new StringBuilder();

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);

            String sCurrentLine;

            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                content.append(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();

                if (fileReader != null)
                    fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content.toString();
    }

    @Override
    public void write(String filePath, String content) {
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(filePath);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();

                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void write(String filePath, byte[] bytes) {
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            outputStream.write(bytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists())
            return null;

        return file;
    }

    @Override
    public InputStream getFileInputStream(String filePath) {
        File file = new File(filePath);

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return inputStream;
    }

    @Override
    public Collection<File> scanFolder(File folder) {
        Collection<File> files = new ArrayList<>();

        File[] objects = folder.listFiles();

        if (objects != null) {
            for (File object : objects) {
                if (!object.isDirectory()) {
                    files.add(object);
                }
            }
        }

        return files;
    }

    @Override
    public Collection<File> scanFolderTree(File folder) {
        Collection<File> files = new ArrayList<>();

        File[] objects = folder.listFiles();

        if (objects != null) {
            for (File object : objects) {
                if (!object.isDirectory()) {
                    files.add(object);
                } else {
                    files.addAll(scanFolderTree(object));
                }
            }
        }

        return files;
    }

    @Override
    public void create(String filePath) {
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move(String from, String to) {
        // TODO
    }

    @Override
    public void copy(String from, String to) {
        // TODO
    }

    @Override
    public void rename(String from, String to) {
        File file1 = new File(from);
        File file2 = new File(to);

        file1.renameTo(file2);
    }

    @Override
    public void delete(String filePath) {
        File file = new File(filePath);
        file.delete();
    }

    @Override
    public void clear(File folder) {
        File[] objects = folder.listFiles();

        if (objects != null) {
            for (File object : objects) {
                clear(object);
            }
        }

        folder.delete();
    }
}
