package com.docuffice.user.infrastructure.adapter.out;

import com.docuffice.auth.application.port.out.UserExistencePort;
import com.docuffice.user.domain.repository.UserRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class UserExistenceAdapter implements UserExistencePort {

    @Inject
    UserRepository userRepository;

    @Override
    public Uni<Boolean> existsById(Long id) {
        return userRepository.findUserById(id)
            .onItem().transform(Objects::nonNull)
            .onFailure().invoke(throwable ->
                log.error("Error checking existence for user id: " + id, throwable))
            .onFailure().recoverWithItem(false);
    }
}
