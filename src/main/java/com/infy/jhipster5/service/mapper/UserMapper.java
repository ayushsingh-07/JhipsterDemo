package com.infy.jhipster5.service.mapper;

import com.infy.jhipster5.domain.User;
import com.infy.jhipster5.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {}
