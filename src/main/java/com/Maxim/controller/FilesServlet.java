package com.Maxim.controller;

import java.time.LocalDate;
import com.Maxim.model.File;
import com.Maxim.service.FileService;
import com.Maxim.view.FileView;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


@WebServlet("/files")
public class FilesServlet extends HttpServlet {
    private FileService fileService = new FileService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<File> files = fileService.getAllFiles();
        resp.setContentType("application/json");
        FileView filesView = new FileView();
        filesView.getFiles(files,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.strip());
        }
        String json = sb.toString();

        System.out.println(json);
        File file = new ObjectMapper().readerFor( File.class).readValue(json);
        LocalDate currentDate = LocalDate.now();
        file.setCreateAt(currentDate.toString());
        fileService.save(file);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file_id = req.getParameter("file_id");
        if(file_id!=null) {
            fileService.deleteFileById(Integer.valueOf(file_id));
        } else {
            resp.sendError(400);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.strip());
        }
        String json = sb.toString();

        File file = new ObjectMapper().readerFor( File.class).readValue(json);

        if (fileService.getFileById(file.getId())!=null) {
            fileService.updateFile(file);
        } else {
            resp.sendError(400,"File not exist");
        }
    }
}
