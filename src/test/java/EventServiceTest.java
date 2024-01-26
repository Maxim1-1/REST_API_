
import com.maxim.model.Event;


import com.maxim.model.File;
import com.maxim.model.Status;
import com.maxim.model.User;
import com.maxim.repository.EventRepository;
import com.maxim.repository.hibernate.HibernateEventRepositoryImpl;

import com.maxim.service.EventService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;
    @Mock
    private final EventRepository eventRepository = new HibernateEventRepositoryImpl();

    @Test
    void getEventsByIdTest() {
        Event event1 = new Event();
        User user = new User();
        File file = new File();
        file.setId(100);
        file.setCreateAt("26.01.2024");
        file.setUpdatedAt("26.01.2024");
        file.setFilePath("/");
        file.setName("test");
        file.setStatus(String.valueOf(Status.ACTIVE));

        event1.setId(100);
        event1.setStatus(String.valueOf(Status.ACTIVE));
        event1.setUser(user);
        event1.setFile(file);

        Mockito.when(eventRepository.getById(100)).thenReturn(event1);
        Event event = eventService.getEventById(100);

        assertEquals(event.getId(), event1.getId());
        assertEquals(event.getStatus(), event1.getStatus());
        assertEquals(event.getFile(), event1.getFile());
        assertEquals(event.getUser(), event1.getUser());

    }

    @Test
    void getAllEventsTest() {
        Event event1 = new Event();
        User user = new User();
        File file = new File();
        file.setStatus(String.valueOf(Status.ACTIVE));
        event1.setId(100);
        event1.setStatus(String.valueOf(Status.ACTIVE));
        event1.setUser(user);
        event1.setFile(file);

        List<Event> events = new ArrayList<>();
        events.add(event1);

        Mockito.when(eventRepository.getAll()).thenReturn(events);
        List<Event> expectedEvents = eventService.getAllEvents();

        assertEquals(expectedEvents.size(), 1);
        assertEquals(expectedEvents, events);
    }

    @Test
    void saveEventTest() {
        Event event1 = new Event();
        User user = new User();
        File file = new File();
        event1.setId(100);
        event1.setStatus(String.valueOf(Status.ACTIVE));
        event1.setUser(user);
        event1.setFile(file);
        eventService.saveEvent(event1);
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void updateEventTest() {
        Event event = new Event();
        User user = new User();
        File file = new File();
        event.setStatus(String.valueOf(Status.ACTIVE));
        event.setUser(user);
        event.setFile(file);

        Event expectedEvent = new Event();
        User expectedUser = new User();
        File expectedFile = new File();
        expectedEvent.setId(1);
        event.setStatus(String.valueOf(Status.ACTIVE));
        event.setUser(expectedUser);
        event.setFile(expectedFile);

        Mockito.when(eventRepository.update(event)).thenReturn(expectedEvent);
        Event eventFromService = eventService.updateEvent(event);

        assertEquals(eventFromService.getId(), 1);
        verify(eventRepository).update(any(Event.class));
    }

    @Test
    void deleteEventByIdTest() {
        eventService.deleteEventById(1);
        verify(eventRepository).deleteById(1);
    }
}