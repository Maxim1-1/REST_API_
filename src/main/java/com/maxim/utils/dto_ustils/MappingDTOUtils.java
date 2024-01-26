package com.maxim.utils.dto_ustils;

import com.maxim.dto.EventDTO;
import com.maxim.dto.FileDTO;
import com.maxim.dto.UserDTO;
import com.maxim.model.Event;
import com.maxim.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MappingDTOUtils {
    private ModelMapper mapper = new ModelMapper();
    public List<UserDTO> convertUserListToUserDTOList(List<User> users) {
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

                    if (event.getFile() != null) {
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

    public List<EventDTO> convertEventListToEventDTOList(List<Event> events) {
        List<EventDTO> eventDTOS = new ArrayList<>();

        for (int i = 0; i<events.size(); i++) {
            Event event = events.get(i);
            EventDTO eventDTO = new EventDTO();
            eventDTO.setId(event.getId());
            eventDTO.setStatus(event.getStatus());
            FileDTO fileDTO = mapper.map(event.getFile(), FileDTO.class);
            eventDTO.setFileDTO(fileDTO);

            eventDTOS.add(eventDTO);
        }
        return eventDTOS;
    }



}
