package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.UserDto;
import com.bookingflight.bookingflight.domain.User;
import com.bookingflight.bookingflight.domain.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
        User user = userService.findById(id);

        UserDto userDto = modelMapper.map(user, UserDto.class);

        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll(){
        List<User> users = userService.findAll();

        List<UserDto> userDtos = new ArrayList<>();

        for(User user : users){
            userDtos.add(modelMapper.map(user, UserDto.class));
        }

        return ResponseEntity.ok().body(userDtos);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User obj){
        User newUser = userService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @PostMapping(value = "/admin")
    public ResponseEntity<User> createAdmin(@RequestBody User obj){
        obj.setAdmin(true);
        User newUser = userService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}