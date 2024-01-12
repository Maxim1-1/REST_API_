package com.Maxim.service;



import com.Maxim.model.User;
import com.Maxim.repository.UserRepository;
import com.Maxim.repository.hibernate.HibernateUserRepositoryImpl;

import java.util.List;

public class UserService {

    private final UserRepository userRepository = new HibernateUserRepositoryImpl();

    public User saveUser(User user) {
            return userRepository.save(user);
    }

    public User getUserById(Integer userId) {
        return userRepository.getById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    public User updateUserById(User user) {
        return userRepository.update(user);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }
}
