package com.lxisoft.store.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.lxisoft.store.domain.Sale} entity.
 */
public class SaleDTO implements Serializable {
    
    private Long id;

    private Long noOfProduct;

    private Instant date;

    private Double amount;


    private Long customerId;

    private Long productId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoOfProduct() {
        return noOfProduct;
    }

    public void setNoOfProduct(Long noOfProduct) {
        this.noOfProduct = noOfProduct;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleDTO)) {
            return false;
        }

        return id != null && id.equals(((SaleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleDTO{" +
            "id=" + getId() +
            ", noOfProduct=" + getNoOfProduct() +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", customerId=" + getCustomerId() +
            ", productId=" + getProductId() +
            "}";
    }
}
