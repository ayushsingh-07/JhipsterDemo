package com.infy.jhipster5.web.rest;

import com.infy.jhipster5.repository.UserRepository;
import com.infy.jhipster5.service.UserService;
import com.infy.jhipster5.service.dto.UserDTO;
import com.infy.jhipster5.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.jhipster5.domain.User}.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "jhipster5User";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;

    private final UserRepository userRepository;

    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * {@code POST  /users} : Create a new user.
     *
     * @param userDTO the userDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDTO, or with status {@code 400 (Bad Request)} if the user has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);
        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDTO result = userService.save(userDTO);
        return ResponseEntity
            .created(new URI("/api/users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /users/:id} : Updates an existing user.
     *
     * @param id the id of the userDTO to save.
     * @param userDTO the userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDTO,
     * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable(value = "id", required = false) final Long id, @RequestBody UserDTO userDTO)
        throws URISyntaxException {
        log.debug("REST request to update User : {}, {}", id, userDTO);
        if (userDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserDTO result = userService.update(userDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /users/:id} : Partial updates given fields of an existing user, field will ignore if it is null
     *
     * @param id the id of the userDTO to save.
     * @param userDTO the userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDTO,
     * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserDTO> partialUpdateUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserDTO userDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update User partially : {}, {}", id, userDTO);
        if (userDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserDTO> result = userService.partialUpdate(userDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /users} : get all the users.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        log.debug("REST request to get all Users");
        return userService.findAll();
    }

    /**
     * {@code GET  /users/:id} : get the "id" user.
     *
     * @param id the id of the userDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        Optional<UserDTO> userDTO = userService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDTO);
    }

    /**
     * {@code DELETE  /users/:id} : delete the "id" user.
     *
     * @param id the id of the userDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        userService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
