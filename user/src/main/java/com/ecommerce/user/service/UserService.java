package com.ecommerce.user.service;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void createUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(userRequest, user);
        userRepository.save(user);
    }

    public Optional<UserResponse> fetchUser(String id) {
        return userRepository.findById(id).map(this::mapToUserResponse);
    }


        public boolean updateUser(String id, UserRequest updatedUser) {
        return userRepository.findById(id)
        .map(existingUser -> {
                    updateUserFromRequest(updatedUser, existingUser);
                    userRepository.save(existingUser);
                    return true;
                }).orElse( false);

    }

    private void updateUserFromRequest(UserRequest userRequest, User user) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName((userRequest.getLastName()));
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());

        if(userRequest.getAddress() != null) {
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setStreet((userRequest.getAddress().getStreet()));
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);
        }
    }
    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }

}
