package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.repository.UserRepository;
import com.ideas2it.ideameds.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<User> getUser(Long userId) {
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
    public Optional<String> deleteUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent() && user.get().getDeletedStatus() != 1) {
            user.get().setDeletedStatus(1);
            User deletedUser = userRepository.save(user.get());
            return Optional.of(deletedUser.getName() + "." +"Deleted Successfully");
        } else {
            return Optional.empty();
        }
    }
}
