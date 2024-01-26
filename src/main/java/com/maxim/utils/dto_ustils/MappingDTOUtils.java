package com.maxim.utils.dto_ustils;

import com.maxim.dto.EventDTO;
import com.maxim.dto.FileDTO;
import com.maxim.dto.UserDTO;
import com.maxim.model.Event;
import com.maxim.model.File;
import com.maxim.model.Status;
import com.maxim.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MappingDTOUtils {
    private ModelMapper mapper = new ModelMapper();

    public EventDTO convertEventToEventDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setStatus(String.valueOf(event.getStatus()));
        eventDTO.setUserDTO(convertUserToUserDTO(event.getUser()));
        eventDTO.setFileDTO(convertFileToFileDTO(event.getFile()));
        return eventDTO;
    }

    public Event convertEventDTOToEvent(EventDTO eventDTO) {
        if (eventDTO!=null) {
            Event event = new Event();
            event.setId(event.getId());
            event.setStatus(String.valueOf(event.getStatus()));
            event.setUser(convertUserDTOToUser(eventDTO.getUserDTO()));
            event.setFile(convertFileDTOToFile(eventDTO.getFileDTO()));
            return event;
        } else return null;

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
        userDTO.setEventsDTO(eventsDTO);
        return userDTO;
    }

    public User convertUserDTOToUser(UserDTO userDTO) {
        if (userDTO!=null) {
            User user = new User();

            user.setId(userDTO.getId());
            user.setName(userDTO.getName());
            user.setStatus(String.valueOf((userDTO.getStatus())));
            List<Event> events = userDTO.getEventsDTO().stream().map(this::convertEventDTOToEvent).collect(Collectors.toList());
            user.setEvents(events);
            return user;
        } else return null;
    }


    public List<UserDTO> convertUserListToUserDTOList(List<User> users){
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = mapper.map(user, UserDTO.class);
            if (user.getEvents() != null) {
                List<EventDTO> eventDTOS = mapper.map(user.getEvents(), new TypeToken<List<EventDTO>>() {}.getType());

                for (EventDTO eventDTO : eventDTOS) {
                    eventDTO.setUserDTO(userDTO);
                }
                for (Event event : user.getEvents()) {
                    EventDTO eventDTO = eventDTOS.stream()
                            .filter(dto -> dto.getId() == event.getId())
                            .findFirst()
                            .orElse(null);

                    if (event.getFile()!=null) {
                        FileDTO fileDTO = mapper.map(event.getFile(), FileDTO.class);
                        eventDTO.setFileDTO(fileDTO);
                    }
                }
                userDTO.setEventsDTO(eventDTOS);
            }
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

}
