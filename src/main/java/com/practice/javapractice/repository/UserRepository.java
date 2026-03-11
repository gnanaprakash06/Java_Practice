package com.practice.javapractice.repository;

import com.practice.javapractice.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    // In-memory DB
    private Map<Long, User> studentDB = new HashMap<>();
    private Long currentId = 1L;

    // Create & Update
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(currentId++);
        }
        studentDB.put(user.getId(), user);
        return user;
    }

    // Read all
    public List<User> findAll() {
        return new ArrayList<>(studentDB.values());
    }

    // Read by id
    public User findById(Long id) {
        return studentDB.get(id);
    }

    // Delete by id
    public boolean deleteById(Long id) {
        if (studentDB.containsKey(id)) {
            studentDB.remove(id);
            return true;
        }
        return false;
    }

    public boolean existsByNameAndDepartment(String name, String department) {
        return studentDB.values().stream().anyMatch(user -> user.getName().equalsIgnoreCase(name) && user.getDepartment().equalsIgnoreCase(department));
    }
}
