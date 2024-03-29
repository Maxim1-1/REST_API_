package com.maxim.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.dto.FileDTO;
import com.maxim.dto.HistoryDTO;
import com.maxim.mapper.FileMapper;
import com.maxim.model.File;
import com.maxim.model.Status;
import com.maxim.service.FileService;
import com.maxim.utils.dto_ustils.MappingDTOUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/api/v1/files/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class FilesRestControllerV1 extends HttpServlet {
    private FileService fileService = new FileService();
    private ModelMapper mapper = new ModelMapper();
    private MappingDTOUtils mappingDTOUtils = new MappingDTOUtils();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo() != null ? req.getPathInfo() : "";
        FileMapper fileMapper = new FileMapper();
        if (path.equals("")) {
            List<File> files = fileService.getAllFiles();
            List<FileDTO> fileDTOS = mapper.map(files, new TypeToken<List<FileDTO>>() {}.getType());;
            resp.setContentType("application/json");
            fileMapper.getFiles(fileDTOS, resp);
        } else if (path.matches("/\\d+")) {
            String id = path.substring(1);
            File file = fileService.getFileById(Integer.parseInt(id));
            if (file != null) {
                resp.setContentType("application/json");
                FileDTO fileDTO = mapper.map(file, FileDTO.class);;
                fileMapper.getFilesById(fileDTO, resp);
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
        String uploadFilePath = applicationPath + "files" + java.io.File.separator ;
        Part filePart = req.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String filePath = uploadFilePath + fileName;
        filePart.write(filePath);
        LocalDate currentDate = LocalDate.now();
        File file = new File();
        file.setStatus(String.valueOf(Status.ACTIVE));
        file.setName(fileName);
        file.setFilePath(filePath);
        file.setUpdatedAt(currentDate.toString());
        file.setCreateAt(currentDate.toString());
        fileService.saveFile(file);
        resp.getWriter().write("Файл успешно загружен");
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
        String file_id = path.substring(1);
        if (path.matches("/\\d+")) {
            if (fileService.getFileById(Integer.valueOf(file_id)) != null) {
                fileService.updateFile(file);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not exist");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


}
