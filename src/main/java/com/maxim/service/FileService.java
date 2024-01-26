package com.maxim.service;


import com.maxim.model.File;
import com.maxim.repository.FileRepository;
import com.maxim.repository.hibernate.HibernateFileRepositoryImpl;

import java.util.List;

public class FileService {

    private  FileRepository hibernateFileRepository = new HibernateFileRepositoryImpl();

    public File saveFile(File file) {
        return hibernateFileRepository.save(file);
    }

    public File getFileById(Integer fileId){
        return hibernateFileRepository.getById(fileId);
    }

    public List<File> getAllFiles() {
        return hibernateFileRepository.getAll();
    }

    public File updateFile(File file) {
        return hibernateFileRepository.update(file);
    }

    public void deleteFileById (Integer id){
        hibernateFileRepository.deleteById(id);
    }
}
