package delivery.service;

import org.springframework.stereotype.Service;

import delivery.User;
import delivery.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = findUserByUsername(username);
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }
        return user;
    }
}
