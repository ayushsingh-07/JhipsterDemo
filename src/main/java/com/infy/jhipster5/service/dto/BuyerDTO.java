package com.infy.jhipster5.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.jhipster5.domain.Buyer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuyerDTO implements Serializable {

    private Long id;

    private String name;

    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuyerDTO)) {
            return false;
        }

        BuyerDTO buyerDTO = (BuyerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buyerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyerDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
