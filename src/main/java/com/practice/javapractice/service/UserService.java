package com.practice.javapractice.service;

import com.practice.javapractice.exception.UserAlreadyExistsException;
import com.practice.javapractice.exception.UserNotFoundException;
import com.practice.javapractice.model.User;
import com.practice.javapractice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        boolean isDuplicate = userRepository.existsByNameAndDepartment(user.getName(), user.getDepartment());
        logger.debug("Duplicate user found: {}", isDuplicate);

        if (isDuplicate) {
            throw new UserAlreadyExistsException("User with the name '" + user.getName() + "' and department '" + user.getDepartment() + "' already exists!");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Cannot update. User not found with ID: " + id));

        if (userDetails.getName() != null && !userDetails.getName().isEmpty()) {
            existingUser.setName(userDetails.getName());
        }

        if (userDetails.getDepartment() != null && !userDetails.getDepartment().isEmpty()) {
            existingUser.setDepartment(userDetails.getDepartment());
        }
        return userRepository.save(existingUser);
    }

    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new UserNotFoundException("Cannot delete. User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
