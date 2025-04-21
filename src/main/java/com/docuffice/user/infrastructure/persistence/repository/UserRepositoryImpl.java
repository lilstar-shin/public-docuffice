package com.docuffice.user.infrastructure.persistence.repository;

import com.docuffice.user.domain.repository.UserRepository;
import com.docuffice.user.infrastructure.persistence.entity.UserEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserRepositoryImpl implements PanacheRepository<UserEntity>, UserRepository {

    @Override
    public Uni<UserEntity> findUserById(Long id) {
        return findById(id);
    }

    @Override
    public Uni<List<UserEntity>> findAllUsers() {
        return listAll();
    }

    @Override
    public Uni<UserEntity> saveUser(UserEntity user) {
        return persist(user);
    }

    @Override
    public Uni<Boolean> deleteUserById(Long id) {
        return deleteById(id);
    }

    @Override
    public Uni<UserEntity> findUserByName(String name) {
        return find("name", name).firstResult();
    }
}
