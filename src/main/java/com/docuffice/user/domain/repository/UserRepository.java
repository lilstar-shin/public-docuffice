package com.docuffice.user.domain.repository;

import com.docuffice.user.infrastructure.persistence.entity.UserEntity;
import io.smallrye.mutiny.Uni;
import java.util.List;

public interface UserRepository {

    Uni<UserEntity> findUserById(Long id);

    Uni<List<UserEntity>> findAllUsers();

    Uni<UserEntity> saveUser(UserEntity user);

    Uni<Boolean> deleteUserById(Long id);

    Uni<UserEntity> findUserByName(String name);
}
