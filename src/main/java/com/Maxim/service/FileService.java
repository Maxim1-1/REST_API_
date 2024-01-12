package com.Maxim.service;


import com.Maxim.model.File;
import com.Maxim.repository.FileRepository;
import com.Maxim.repository.hibernate.HibernateFileRepositoryImpl;

import java.util.List;

public class FileService {

    private final FileRepository hibernateFileRepository = new HibernateFileRepositoryImpl();

    public File save(File file) {
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
