import com.maxim.model.Event;
import com.maxim.model.File;
import com.maxim.model.Status;
import com.maxim.model.User;
import com.maxim.repository.EventRepository;
import com.maxim.repository.UserRepository;
import com.maxim.repository.hibernate.HibernateEventRepositoryImpl;
import com.maxim.repository.hibernate.HibernateUserRepositoryImpl;
import com.maxim.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private final UserRepository userRepository = new HibernateUserRepositoryImpl();

    @Test
    void getUserByIdTest() {
        User user = new User();
        File file = new File();
        Event event = new Event();
        List<Event> events = new ArrayList<>();
        events.add(event);

        event.setFile(file);
        event.setUser(user);
        user.setId(1);
        user.setName("test");
        user.setEvents(events);

        Mockito.when(userRepository.getById(1)).thenReturn(user);
        User expectedUser = userService.getUserById(1);

        assertEquals(user.getId(), expectedUser.getId());
        assertEquals(user.getEvents(), expectedUser.getEvents());
        assertEquals(user.getName(), expectedUser.getName());

    }

    @Test
    void getAllUsersTest() {
        User user = new User();

        List<User> users = new ArrayList<>();
        users.add(user);

        Mockito.when(userRepository.getAll()).thenReturn(users);
        List<User> expecteUsers = userService.getAllUsers();

        assertEquals(expecteUsers.size(), 1);
        assertEquals(expecteUsers, users);
    }

    @Test
    void saveUserTest() {
        User user = new User();

        userService.saveUser(user);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUserByIdTest() {
        User user = new User();
        User expectedUser = new User();

        expectedUser.setId(1);
        Mockito.when(userRepository.update(user)).thenReturn(expectedUser);
        User userFromService = userService.updateUserById(user);

        assertEquals(userFromService.getId(), 1);
        verify(userRepository).update(any(User.class));
    }

    @Test
    void deleteEventByIdTest() {
        userService.deleteUserById(1);
        verify(userRepository).deleteById(1);
    }
}