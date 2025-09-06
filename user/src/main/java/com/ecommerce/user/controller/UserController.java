package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {

        return userService.fetchUser(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users") ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {

        userService.createUser(userRequest);
        return ResponseEntity.ok("User added successfully!");
    }


    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUser (@PathVariable String id, @RequestBody UserRequest updatedUser){
        boolean updated = userService.updateUser(id, updatedUser);
        if(updated)
            return ResponseEntity.ok("User updated successfully");
        return ResponseEntity.notFound().build();
    }

}
