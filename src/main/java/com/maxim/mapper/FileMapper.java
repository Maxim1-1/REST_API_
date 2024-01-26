package com.maxim.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxim.dto.FileDTO;
import com.maxim.dto.HistoryDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileMapper {
    private ObjectMapper mapper = new ObjectMapper();
    public void getFiles(List<FileDTO> files, HttpServletResponse response) throws IOException {
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(files));
    }

    public void getFilesById(FileDTO file, HttpServletResponse response) throws IOException {
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(file));
    }

    public void getHistoryFiles(List<HistoryDTO> historyDTO, HttpServletResponse response) throws IOException {
        PrintWriter messageWriter = response.getWriter();
        messageWriter.print(mapper.writeValueAsString(historyDTO));
    }
}
