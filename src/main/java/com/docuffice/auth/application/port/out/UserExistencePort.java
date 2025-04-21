package com.docuffice.auth.application.port.out;

import io.smallrye.mutiny.Uni;

public interface UserExistencePort {

    Uni<Boolean> existsById(Long id);
}
