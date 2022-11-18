package com.ideas2it.ideameds.service;

import com.ideas2it.ideameds.Repository.UserRepository;
import com.ideas2it.ideameds.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service of User.
 *
 * @author - Parthasarathy Elumalai
 * @version - 1.0
 * @since - 2022-11-18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public String updateUser(User user) {
        User updatedUser = userRepository.save(user);
        return updatedUser.getName() +" ."+ "Updated Successfully";
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).get();
        if(user != null) {
            user.setDeletedStatus(true);
        }
        User user1 = userRepository.save(user);
        return "Deleted Successfully";
    }
}
