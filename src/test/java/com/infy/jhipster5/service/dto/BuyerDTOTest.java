package com.infy.jhipster5.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.infy.jhipster5.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyerDTO.class);
        BuyerDTO buyerDTO1 = new BuyerDTO();
        buyerDTO1.setId(1L);
        BuyerDTO buyerDTO2 = new BuyerDTO();
        assertThat(buyerDTO1).isNotEqualTo(buyerDTO2);
        buyerDTO2.setId(buyerDTO1.getId());
        assertThat(buyerDTO1).isEqualTo(buyerDTO2);
        buyerDTO2.setId(2L);
        assertThat(buyerDTO1).isNotEqualTo(buyerDTO2);
        buyerDTO1.setId(null);
        assertThat(buyerDTO1).isNotEqualTo(buyerDTO2);
    }
}
