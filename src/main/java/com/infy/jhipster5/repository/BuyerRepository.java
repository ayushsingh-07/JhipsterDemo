package com.infy.jhipster5.repository;

import com.infy.jhipster5.domain.Buyer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Buyer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {}
