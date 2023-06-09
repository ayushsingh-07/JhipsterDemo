package com.infy.jhipster5.web.rest;

import com.infy.jhipster5.repository.BuyerRepository;
import com.infy.jhipster5.service.BuyerService;
import com.infy.jhipster5.service.dto.BuyerDTO;
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
 * REST controller for managing {@link com.infy.jhipster5.domain.Buyer}.
 */
@RestController
@RequestMapping("/api")
public class BuyerResource {

    private final Logger log = LoggerFactory.getLogger(BuyerResource.class);

    private static final String ENTITY_NAME = "jhipster5Buyer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyerService buyerService;

    private final BuyerRepository buyerRepository;

    public BuyerResource(BuyerService buyerService, BuyerRepository buyerRepository) {
        this.buyerService = buyerService;
        this.buyerRepository = buyerRepository;
    }

    /**
     * {@code POST  /buyers} : Create a new buyer.
     *
     * @param buyerDTO the buyerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyerDTO, or with status {@code 400 (Bad Request)} if the buyer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/buyers")
    public ResponseEntity<BuyerDTO> createBuyer(@RequestBody BuyerDTO buyerDTO) throws URISyntaxException {
        log.debug("REST request to save Buyer : {}", buyerDTO);
        if (buyerDTO.getId() != null) {
            throw new BadRequestAlertException("A new buyer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuyerDTO result = buyerService.save(buyerDTO);
        return ResponseEntity
            .created(new URI("/api/buyers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /buyers/:id} : Updates an existing buyer.
     *
     * @param id the id of the buyerDTO to save.
     * @param buyerDTO the buyerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyerDTO,
     * or with status {@code 400 (Bad Request)} if the buyerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/buyers/{id}")
    public ResponseEntity<BuyerDTO> updateBuyer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BuyerDTO buyerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Buyer : {}, {}", id, buyerDTO);
        if (buyerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BuyerDTO result = buyerService.update(buyerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /buyers/:id} : Partial updates given fields of an existing buyer, field will ignore if it is null
     *
     * @param id the id of the buyerDTO to save.
     * @param buyerDTO the buyerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyerDTO,
     * or with status {@code 400 (Bad Request)} if the buyerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buyerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buyerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/buyers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BuyerDTO> partialUpdateBuyer(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BuyerDTO buyerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Buyer partially : {}, {}", id, buyerDTO);
        if (buyerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuyerDTO> result = buyerService.partialUpdate(buyerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /buyers} : get all the buyers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyers in body.
     */
    @GetMapping("/buyers")
    public List<BuyerDTO> getAllBuyers() {
        log.debug("REST request to get all Buyers");
        return buyerService.findAll();
    }

    /**
     * {@code GET  /buyers/:id} : get the "id" buyer.
     *
     * @param id the id of the buyerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/buyers/{id}")
    public ResponseEntity<BuyerDTO> getBuyer(@PathVariable Long id) {
        log.debug("REST request to get Buyer : {}", id);
        Optional<BuyerDTO> buyerDTO = buyerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyerDTO);
    }

    /**
     * {@code DELETE  /buyers/:id} : delete the "id" buyer.
     *
     * @param id the id of the buyerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/buyers/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        log.debug("REST request to delete Buyer : {}", id);
        buyerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
