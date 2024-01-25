package com.maxim.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;


public class EventDTO {

    private Integer id;

    @JsonBackReference
    private UserDTO userDTO;
    private FileDTO fileDTO;
    public Integer getId() {
        return id;
    }
    private String status;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return userDTO;
    }

    public void setUser(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public FileDTO getFileDTO() {
        return fileDTO;
    }

    public void setFileDTO(FileDTO fileDTO) {
        this.fileDTO = fileDTO;
    }

}
