package com.Maxim.controller;

import com.Maxim.DTO.HistoryDTO;
import com.Maxim.model.File;
import com.Maxim.model.User;
import com.Maxim.service.FileService;
import com.Maxim.view.FileView;
import com.Maxim.view.UserView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/files/history")
public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileService fileService = new FileService();
        List<File> files = fileService.getAllFiles();
        List<HistoryDTO> historyDTOS = new ArrayList<>();

        for (File file:files) {
            if (file.getCreateAt()!=null) {
                HistoryDTO historyDTO = new HistoryDTO();
                historyDTO.setId(file.getId());
                historyDTO.setName(file.getName());
                historyDTO.setCreate_at(file.getCreateAt());
                historyDTOS.add(historyDTO);
            }
        }
        FileView filesView = new FileView();
        resp.setContentType("application/json");
        filesView.getHistoryFiles(historyDTOS,resp);
    }
}
