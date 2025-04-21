package com.docuffice.user.domain.mapper;

import com.docuffice.user.domain.model.User;
import com.docuffice.user.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface UserDomainMapper {

    /*
     * Entity -> Model
     */
    User userEntityToUserModel(UserEntity entity);

    /**
     * Model -> Entity
     */
    @Mapping(target = "userAuthentication", ignore = true)
    UserEntity userModelToUserEntity(User model);
}
