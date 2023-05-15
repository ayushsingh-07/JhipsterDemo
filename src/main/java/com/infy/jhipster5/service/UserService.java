package com.infy.jhipster5.service;

import com.infy.jhipster5.service.dto.UserDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.jhipster5.domain.User}.
 */
public interface UserService {
    /**
     * Save a user.
     *
     * @param userDTO the entity to save.
     * @return the persisted entity.
     */
    UserDTO save(UserDTO userDTO);

    /**
     * Updates a user.
     *
     * @param userDTO the entity to update.
     * @return the persisted entity.
     */
    UserDTO update(UserDTO userDTO);

    /**
     * Partially updates a user.
     *
     * @param userDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserDTO> partialUpdate(UserDTO userDTO);

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    List<UserDTO> findAll();

    /**
     * Get the "id" user.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDTO> findOne(Long id);

    /**
     * Delete the "id" user.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
