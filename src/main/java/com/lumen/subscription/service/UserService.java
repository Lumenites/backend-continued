package com.lumen.subscription.service;

import com.lumen.subscription.entity.User;
import com.lumen.subscription.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1); // nodeId = 1

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Create new user (password encoded)
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(idGenerator.nextId());
        return userRepository.save(user);
    }

    // ✅ Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Get user by ID
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ✅ Update user
    public User updateUser(String id, User updatedUser) {
        User user = getUserById(id);

        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if (updatedUser.getRoles() != null && !updatedUser.getRoles().isEmpty()) {
            user.setRoles(updatedUser.getRoles());
        }

        return userRepository.save(user);
    }

    // ✅ Delete user
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}