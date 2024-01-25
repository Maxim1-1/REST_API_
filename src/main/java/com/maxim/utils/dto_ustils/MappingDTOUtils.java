package com.maxim.utils.dto_ustils;

import com.maxim.dto.EventDTO;
import com.maxim.dto.FileDTO;
import com.maxim.dto.UserDTO;
import com.maxim.model.Event;
import com.maxim.model.File;
import com.maxim.model.Status;
import com.maxim.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class MappingDTOUtils {

    public EventDTO convertEventToEventDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setStatus(String.valueOf(event.getStatus()));
        eventDTO.setUser(convertUserToUserDTO(event.getUser()));
        eventDTO.setFile(convertFileToFileDTO(event.getFile()));
        return eventDTO;
    }

    public Event convertEventDTOToEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setId(event.getId());
        event.setStatus(String.valueOf(event.getStatus()));
        event.setUser(convertUserDTOToUser(eventDTO.getUser()));
        event.setFile(convertFileDTOToFile(eventDTO.getFile()));
        return event;
    }


    public File convertFileDTOToFile(FileDTO fileDTO) {
        File file = new File();

        file.setId(fileDTO.getId());
        file.setName(fileDTO.getName());
        file.setCreateAt(fileDTO.getCreateAt());
        file.setUpdatedAt(fileDTO.getUpdatedAt());
        file.setStatus(String.valueOf(Status.valueOf(fileDTO.getStatus())));
        file.setFilePath(fileDTO.getFilePath());
        return file;
    }

    public FileDTO convertFileToFileDTO(File file) {
        FileDTO fileDTO = new FileDTO();

        fileDTO.setId(file.getId());
        fileDTO.setName(file.getName());
        fileDTO.setCreateAt(file.getCreateAt());
        fileDTO.setUpdatedAt(file.getUpdatedAt());
        fileDTO.setStatus(String.valueOf(Status.valueOf(file.getStatus())));
        fileDTO.setFilePath(file.getFilePath());
        return fileDTO;
    }

    public UserDTO convertUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setStatus(String.valueOf(user.getStatus()));
        List<EventDTO> eventsDTO = user.getEvents().stream().map(this::convertEventToEventDTO).collect(Collectors.toList());
        userDTO.setEvents(eventsDTO);
        return userDTO;
    }

    public User convertUserDTOToUser(UserDTO userDTO) {
        if (userDTO!=null) {
            User user = new User();

            user.setId(userDTO.getId());
            user.setName(userDTO.getName());
            user.setStatus(String.valueOf((userDTO.getStatus())));
            List<Event> events = userDTO.getEvents().stream().map(this::convertEventDTOToEvent).collect(Collectors.toList());
            user.setEvents(events);
            return user;
        } else return null;
    }



}
