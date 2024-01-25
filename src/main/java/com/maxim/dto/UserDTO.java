package com.maxim.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.maxim.model.Status;

import java.util.List;


public class UserDTO {

    private Integer id;

    private String name;
    private String status;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonManagedReference
    private  List<EventDTO> eventsDTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventDTO> getEvents() {
        return eventsDTO;
    }

    public void setEvents(List<EventDTO> eventDTOS) {
        this.eventsDTO = eventDTOS;
    }
}
