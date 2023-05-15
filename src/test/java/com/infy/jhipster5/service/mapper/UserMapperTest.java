package com.infy.jhipster5.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
    }
}
