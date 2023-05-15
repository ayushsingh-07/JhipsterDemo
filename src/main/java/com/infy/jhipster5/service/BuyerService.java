package com.infy.jhipster5.service;

import com.infy.jhipster5.service.dto.BuyerDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.jhipster5.domain.Buyer}.
 */
public interface BuyerService {
    /**
     * Save a buyer.
     *
     * @param buyerDTO the entity to save.
     * @return the persisted entity.
     */
    BuyerDTO save(BuyerDTO buyerDTO);

    /**
     * Updates a buyer.
     *
     * @param buyerDTO the entity to update.
     * @return the persisted entity.
     */
    BuyerDTO update(BuyerDTO buyerDTO);

    /**
     * Partially updates a buyer.
     *
     * @param buyerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuyerDTO> partialUpdate(BuyerDTO buyerDTO);

    /**
     * Get all the buyers.
     *
     * @return the list of entities.
     */
    List<BuyerDTO> findAll();

    /**
     * Get the "id" buyer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuyerDTO> findOne(Long id);

    /**
     * Delete the "id" buyer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
