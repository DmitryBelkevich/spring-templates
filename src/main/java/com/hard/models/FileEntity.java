package com.hard.models;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "file_path")
    private String filePath;

    public FileEntity() {

    }

    public FileEntity(String filePath) {
        this.filePath = filePath;
    }

    public FileEntity(long id, String filePath) {
        this.id = id;
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
