
import com.maxim.model.Event;


import com.maxim.model.File;
import com.maxim.model.Status;
import com.maxim.model.User;
import com.maxim.repository.hibernate.HibernateEventRepositoryImpl;

import com.maxim.service.EventService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService = new EventService();
    @Mock
    private Event eventMock = new Event();
    @InjectMocks
    private HibernateEventRepositoryImpl eventRepository;

    @BeforeEach
    public void setup() {
//        MockitoAnnotations.openMocks(this);
    }

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

        when(eventRepository.getById(100)).thenReturn(event1);
        Event event = eventService.getEventById(100);

        assertEquals(event.getId(), event.getId());
        verify(eventService).getEventById(1);

    }
}