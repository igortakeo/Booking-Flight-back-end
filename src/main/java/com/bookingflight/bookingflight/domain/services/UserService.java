package com.bookingflight.bookingflight.domain.services;

import com.bookingflight.bookingflight.domain.User;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectAlreadyExistException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotAllowedToBeDeletedException;
import com.bookingflight.bookingflight.domain.services.exceptions.ObjectNotFoundException;
import com.bookingflight.bookingflight.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        Optional<User> obj = userRepository.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found with Id = " + id));
    }


    public User create(User obj) {

        User user = userRepository.findByUsername(obj.getUsername());

        if(user != null){
            throw new ObjectAlreadyExistException("Object already exist");
        }

        obj.setId(null);
        obj.setPassword(bCryptPasswordEncoder.encode(obj.getPassword()));
        return userRepository.save(obj);
    }

    public void delete(Long id) {
        User user = findById(id);

        if(user.getUsername().equals("igortakeo")){
            throw new ObjectNotAllowedToBeDeletedException("Object not allowed to be deleted");
        }

        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}