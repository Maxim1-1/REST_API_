package com.Maxim.controller;


import com.Maxim.model.File;
import com.Maxim.service.FileService;
import com.Maxim.view.FileView;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/files/id")
public class FileByIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String file_id = req.getParameter("file_id");


        if(file_id !=null) {

            FileService fileService = new FileService();
            File file = fileService.getFileById(Integer.valueOf(file_id));

            resp.setContentType("application/json");
            FileView fileView = new FileView();
            fileView.getFilesById(file,resp);
        } else {
            resp.sendError(400);
        }
        
    }
}


