package com.infy.jhipster5.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.infy.jhipster5.IntegrationTest;
import com.infy.jhipster5.domain.Buyer;
import com.infy.jhipster5.repository.BuyerRepository;
import com.infy.jhipster5.service.dto.BuyerDTO;
import com.infy.jhipster5.service.mapper.BuyerMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BuyerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String ENTITY_API_URL = "/api/buyers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private BuyerMapper buyerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyerMockMvc;

    private Buyer buyer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Buyer createEntity(EntityManager em) {
        Buyer buyer = new Buyer().name(DEFAULT_NAME).quantity(DEFAULT_QUANTITY);
        return buyer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Buyer createUpdatedEntity(EntityManager em) {
        Buyer buyer = new Buyer().name(UPDATED_NAME).quantity(UPDATED_QUANTITY);
        return buyer;
    }

    @BeforeEach
    public void initTest() {
        buyer = createEntity(em);
    }

    @Test
    @Transactional
    void createBuyer() throws Exception {
        int databaseSizeBeforeCreate = buyerRepository.findAll().size();
        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);
        restBuyerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyerDTO)))
            .andExpect(status().isCreated());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeCreate + 1);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBuyer.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    void createBuyerWithExistingId() throws Exception {
        // Create the Buyer with an existing ID
        buyer.setId(1L);
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        int databaseSizeBeforeCreate = buyerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBuyers() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        // Get all the buyerList
        restBuyerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }

    @Test
    @Transactional
    void getBuyer() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        // Get the buyer
        restBuyerMockMvc
            .perform(get(ENTITY_API_URL_ID, buyer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    void getNonExistingBuyer() throws Exception {
        // Get the buyer
        restBuyerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuyer() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();

        // Update the buyer
        Buyer updatedBuyer = buyerRepository.findById(buyer.getId()).get();
        // Disconnect from session so that the updates on updatedBuyer are not directly saved in db
        em.detach(updatedBuyer);
        updatedBuyer.name(UPDATED_NAME).quantity(UPDATED_QUANTITY);
        BuyerDTO buyerDTO = buyerMapper.toDto(updatedBuyer);

        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuyer.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void putNonExistingBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(count.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(count.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(buyerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(count.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(buyerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuyerWithPatch() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();

        // Update the buyer using partial update
        Buyer partialUpdatedBuyer = new Buyer();
        partialUpdatedBuyer.setId(buyer.getId());

        partialUpdatedBuyer.name(UPDATED_NAME).quantity(UPDATED_QUANTITY);

        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyer))
            )
            .andExpect(status().isOk());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuyer.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void fullUpdateBuyerWithPatch() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();

        // Update the buyer using partial update
        Buyer partialUpdatedBuyer = new Buyer();
        partialUpdatedBuyer.setId(buyer.getId());

        partialUpdatedBuyer.name(UPDATED_NAME).quantity(UPDATED_QUANTITY);

        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBuyer))
            )
            .andExpect(status().isOk());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuyer.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void patchNonExistingBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(count.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(count.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(buyerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();
        buyer.setId(count.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(buyerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuyer() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        int databaseSizeBeforeDelete = buyerRepository.findAll().size();

        // Delete the buyer
        restBuyerMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
