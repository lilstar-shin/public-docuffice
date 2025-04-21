package com.docuffice.user.application.service;

import com.docuffice.user.application.dto.request.CreateUserRequestDTO;
import com.docuffice.user.application.dto.response.UserResponseDTO;
import com.docuffice.user.application.mapper.UserApplicationMapper;
import com.docuffice.user.domain.service.UserDomainService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@ApplicationScoped
public class UserService {

    @Inject
    UserDomainService userDomainService;

    @Inject
    UserApplicationMapper userMapper;

    public Uni<UserResponseDTO> createUser(@Valid CreateUserRequestDTO user) {
        return userDomainService.createUser(userMapper.userRequestDTOToUserModel(user))
            .map(userMapper::userModelToUserResponseDTO);
    }

    public Uni<UserResponseDTO> getUserById(Long id) {
        return userDomainService.getUserById(id)
            .map(userMapper::userModelToUserResponseDTO);
    }
//
//    public List<User> getAllUsers() {
//        return userDomainService.getAllUsers();
//    }
//
//    @Transactional
//    public Optional<User> updateUser(Long id, User updatedUserData) {
//        return userDomainService.updateUser(id, updatedUserData);
//    }
//
//    @Transactional
//    public boolean deleteUser(Long id) {
//        return userDomainService.deleteUser(id);
//    }
//
//    public Optional<User> findUserByName(String name) {
//        return userDomainService.findUserByName(name);
//    }
}




