package com.maxim.controller;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import com.maxim.model.File;
import com.maxim.dto.HistoryDTO;
import com.maxim.mapper.EventMapper;
import com.maxim.model.Event;

import java.nio.file.Path;

import com.maxim.model.Status;
import com.maxim.service.FileService;
import com.maxim.mapper.FileMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@WebServlet("/api/v1/files/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class FilesRestControllerV1 extends HttpServlet {
    private FileService fileService = new FileService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        if (path.equals("")) {
            List<File> files = fileService.getAllFiles();
            resp.setContentType("application/json");
            FileMapper fileMapper = new FileMapper();
            fileMapper.getFiles(files, resp);
        } else if (path.matches("/\\d+")) {
            String id = path.substring(1);
            File file = fileService.getFileById(Integer.parseInt(id));
            if (file != null) {
                resp.setContentType("application/json");
                FileMapper fileMapper = new FileMapper();
                fileMapper.getFilesById(file, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else if (path.matches("/history")) {
            List<File> files = fileService.getAllFiles();
            List<HistoryDTO> historyDTOS = new ArrayList<>();

            for (File file : files) {
                if (file.getCreateAt() != null) {
                    HistoryDTO historyDTO = new HistoryDTO();
                    historyDTO.setId(file.getId());
                    historyDTO.setName(file.getName());
                    historyDTO.setCreate_at(file.getCreateAt());
                    historyDTOS.add(historyDTO);
                }
            }
            FileMapper filesView = new FileMapper();
            resp.setContentType("application/json");
            filesView.getHistoryFiles(historyDTOS, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String applicationPath = req.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + java.io.File.separator + "uploads";

        java.io.File fileSaveDir = new java.io.File(uploadFilePath);

//        System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());
        Part filePart = req.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        filePart.write(uploadFilePath + java.io.File.separator + fileName);


//        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
//        ServletFileUpload uploader = null;
//        Part filePart = req.getPart("file");
//        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//
//
//        try (InputStream fileContent = filePart.getInputStream()) {

//            File file = new File(request.getServletContext().getAttribute("FILES_DIR")+File.separator+fileItem.getName());
//            String filePath = new java.io.File("").getAbsolutePath() + "src/main/resources/files" + fileName;
//        java.io.File filePath = new java.io.File(req.getServletContext().getAttribute("FILES_DIR") + java.io.File.separator + fileName);


//            Files.copy(fileContent, Paths.get(filePath));
//            LocalDate currentDate = LocalDate.now();
//            File file = new File();
//            file.setStatus(Status.ACTIVE);
//            file.setName(fileName);
//            file.setFilePath(filePath);
//            file.setUpdatedAt(currentDate.toString());
//            file.setUpdatedAt(currentDate.toString());
//            fileService.save(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        resp.getWriter().write("Файл успешно загружен");
    }



    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        if (path.matches("/\\d+")) {
            String file_id = path.substring(1);
            File file = fileService.getFileById(Integer.parseInt(file_id));

            if (file != null) {
                fileService.deleteFileById(Integer.valueOf(file_id));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not exist");
            }

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
        File file = new ObjectMapper().readerFor(File.class).readValue(json);
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        if (path.matches("/\\d+")) {
            if (fileService.getFileById(file.getId()) != null) {
                fileService.updateFile(file);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not exist");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


}
