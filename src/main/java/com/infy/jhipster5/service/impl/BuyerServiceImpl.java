package com.infy.jhipster5.service.impl;

import com.infy.jhipster5.domain.Buyer;
import com.infy.jhipster5.repository.BuyerRepository;
import com.infy.jhipster5.service.BuyerService;
import com.infy.jhipster5.service.dto.BuyerDTO;
import com.infy.jhipster5.service.mapper.BuyerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Buyer}.
 */
@Service
@Transactional
public class BuyerServiceImpl implements BuyerService {

    private final Logger log = LoggerFactory.getLogger(BuyerServiceImpl.class);

    private final BuyerRepository buyerRepository;

    private final BuyerMapper buyerMapper;

    public BuyerServiceImpl(BuyerRepository buyerRepository, BuyerMapper buyerMapper) {
        this.buyerRepository = buyerRepository;
        this.buyerMapper = buyerMapper;
    }

    @Override
    public BuyerDTO save(BuyerDTO buyerDTO) {
        log.debug("Request to save Buyer : {}", buyerDTO);
        Buyer buyer = buyerMapper.toEntity(buyerDTO);
        buyer = buyerRepository.save(buyer);
        return buyerMapper.toDto(buyer);
    }

    @Override
    public BuyerDTO update(BuyerDTO buyerDTO) {
        log.debug("Request to update Buyer : {}", buyerDTO);
        Buyer buyer = buyerMapper.toEntity(buyerDTO);
        buyer = buyerRepository.save(buyer);
        return buyerMapper.toDto(buyer);
    }

    @Override
    public Optional<BuyerDTO> partialUpdate(BuyerDTO buyerDTO) {
        log.debug("Request to partially update Buyer : {}", buyerDTO);

        return buyerRepository
            .findById(buyerDTO.getId())
            .map(existingBuyer -> {
                buyerMapper.partialUpdate(existingBuyer, buyerDTO);

                return existingBuyer;
            })
            .map(buyerRepository::save)
            .map(buyerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BuyerDTO> findAll() {
        log.debug("Request to get all Buyers");
        return buyerRepository.findAll().stream().map(buyerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BuyerDTO> findOne(Long id) {
        log.debug("Request to get Buyer : {}", id);
        return buyerRepository.findById(id).map(buyerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Buyer : {}", id);
        buyerRepository.deleteById(id);
    }
}
