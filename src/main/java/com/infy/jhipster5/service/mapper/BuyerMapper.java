package com.infy.jhipster5.service.mapper;

import com.infy.jhipster5.domain.Buyer;
import com.infy.jhipster5.service.dto.BuyerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Buyer} and its DTO {@link BuyerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BuyerMapper extends EntityMapper<BuyerDTO, Buyer> {}
