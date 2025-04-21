package com.docuffice.user.domain.service;

import com.docuffice.user.domain.mapper.UserDomainMapper;
import com.docuffice.user.domain.model.User;
import com.docuffice.user.domain.repository.UserRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

@ApplicationScoped
public class UserDomainService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserDomainMapper userDomainMapper;

    @WithSession
    public Uni<User> createUser(@Valid User user) {
        return userRepository.saveUser(userDomainMapper.userModelToUserEntity(user))
            .map(userDomainMapper::userEntityToUserModel);
    }

    @WithSession
    public Uni<User> getUserById(Long id) {
        return userRepository.findUserById(id).map(userDomainMapper::userEntityToUserModel);
    }
//
//    public Uni<List<User>> getAllUsers() {
//        return userRepository.findAllUsers().map(userEntityList -> userEntityList.stream()
//                .map(userDomainMapper::userEntityToUserModel).toList());
//    }
//
//    public Uni<Optional<User>> updateUser(Long id, User updatedUserData) {
//
//    }
//
//    public Uni<Boolean> deleteUser(Long id) {
//        return userRepository.deleteById(id);
//    }
//
//    public Uni<Optional<User>> findUserByName(String name) {
//        return userRepository.findByName(name).map(optionalUserEntity -> optionalUserEntity
//                .map(userDomainMapper::userEntityToUserModel));
//    }
}
