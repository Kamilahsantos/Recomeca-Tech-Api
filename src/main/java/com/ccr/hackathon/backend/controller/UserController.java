package com.ccr.hackathon.backend.controller;

import com.ccr.hackathon.backend.exception.ResourceNotFoundException;
import com.ccr.hackathon.backend.model.User;
import com.ccr.hackathon.backend.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/v1")
@RestController
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "user successfully created"),
            @ApiResponse(code = 500, message = "An internal error occurred, it was not possible to complete your request")
    }
    )
    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        log.info("Creating a new user with name {} ", user.getName());
        return userRepository.save(user);
    }


    @ApiOperation(value = "List all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "users  was listed with success"),
            @ApiResponse(code = 500, message = "An internal error occurred , it was not possible to complete your request")
    }
    )
    @GetMapping("/user")
    public List<User> getAllTechUsers() {
        log.info("Listing all Tech content");
        return userRepository.findAll();
    }


    @ApiOperation(value = "Find a user by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user find with success"),
            @ApiResponse(code = 500, message = "An internal error occurred , it was not possible to complete your request")
    }
    )
    @GetMapping("user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
        log.info("Find a Tech content with id {} ", userId);

        return ResponseEntity.ok().body(user);
    }


    @ApiOperation(value = "Update a user by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user updated with success"),
            @ApiResponse(code = 500, message = "An internal error occurred w it was not possible to complete your request")
    }
    )
    @PatchMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));


        user.setFamilyIncome(userDetails.getFamilyIncome());
        user.setDocumentNumber(userDetails.getDocumentNumber());
        user.setGender(userDetails.getGender());
        user.setName(userDetails.getName());
        user.setScholarity(userDetails.getScholarity());
        user.setTrack(userDetails.getTrack());
        user.setAddress(userDetails.getAddress());
        user.setBirthDate(userDetails.getBirthDate());
        user.setExperience(userDetails.getExperience());
        user.setProjects(userDetails.getProjects());
        user.setPhoneNumber(userDetails.getPhoneNumber());


        final User updateUser = userRepository.save(
                user
        );
        log.info("Soft skills  content with id  {} was successfully updated", userId);
        return ResponseEntity.ok(updateUser);
    }


    @ApiOperation(value = "Delete a user by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "user deleted with success"),
            @ApiResponse(code = 500, message = "An internal error occurred , it was not possible to complete your request")
    }
    )
    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        log.info("Tech Content with id {}  deleted with success ", userId);
        return response;
    }
}
