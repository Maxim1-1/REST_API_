package com.Maxim.view;

import com.Maxim.DTO.HistoryDTO;
import com.Maxim.model.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileView {

    public void getFiles(List<File> files, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(files));
    }

    public void getFilesById(File file, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(file));
    }

    public void getHistoryFiles(List<HistoryDTO> historyDTO, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(historyDTO));
    }
}
