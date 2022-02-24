package com.bookingflight.bookingflight.controllers;

import com.bookingflight.bookingflight.controllers.dto.UserResponseDto;
import com.bookingflight.bookingflight.domain.User;
import com.bookingflight.bookingflight.domain.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "User")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Get an user by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        User user = userService.findById(id);

        UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

        return ResponseEntity.ok().body(userResponseDto);
    }

    @ApiOperation(value = "Get all users")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){
        List<User> users = userService.findAll();

        List<UserResponseDto> userResponseDtos = new ArrayList<>();

        for(User user : users){
            userResponseDtos.add(modelMapper.map(user, UserResponseDto.class));
        }

        return ResponseEntity.ok().body(userResponseDtos);
    }

    @ApiOperation(value = "Create new user")
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User obj){
        User newUser = userService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @ApiOperation(value = "Create an admin")
    @PostMapping(value = "/admin")
    public ResponseEntity<User> createAdmin(@RequestBody User obj){
        obj.setAdmin(true);
        User newUser = userService.create(obj);
        URI endpoint = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(endpoint).build();
    }

    @ApiOperation(value = "Delete an user")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}