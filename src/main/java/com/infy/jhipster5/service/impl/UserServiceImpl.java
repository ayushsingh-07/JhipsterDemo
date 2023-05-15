package com.infy.jhipster5.service.impl;

import com.infy.jhipster5.domain.User;
import com.infy.jhipster5.repository.UserRepository;
import com.infy.jhipster5.service.UserService;
import com.infy.jhipster5.service.dto.UserDTO;
import com.infy.jhipster5.service.mapper.UserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link User}.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        log.debug("Request to save User : {}", userDTO);
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        log.debug("Request to update User : {}", userDTO);
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public Optional<UserDTO> partialUpdate(UserDTO userDTO) {
        log.debug("Request to partially update User : {}", userDTO);

        return userRepository
            .findById(userDTO.getId())
            .map(existingUser -> {
                userMapper.partialUpdate(existingUser, userDTO);

                return existingUser;
            })
            .map(userRepository::save)
            .map(userMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        log.debug("Request to get all Users");
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findOne(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id).map(userMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }
}
