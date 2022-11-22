package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service of User.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> addUser(User user) {
        Optional<User> savedUser = Optional.of(userRepository.save(user));

        if (savedUser.isPresent()) {
            return savedUser;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent() && (userOptional.get().getDeletedStatus() != 1)) {
            return userOptional;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<String> updateUser(User user) {
        User updatedUser = userRepository.save(user);

        if (null != updatedUser) {
            return Optional.of(updatedUser.getName() + " ." + "Updated Successfully");
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> deleteUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getUserId());
        if (userOptional.isPresent()) {
            Optional<User> deleteUser = Optional.of(userRepository.save(user));
            if (deleteUser.isPresent()) {
                return Optional.of(deleteUser.get().getName() + "." +"Deleted Successfully");
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean isUserExist(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public List<String> getUserPhoneNumber() {
        return userRepository.findAll().stream().map(User::getPhoneNumber).collect(Collectors.toList());
    }
}
