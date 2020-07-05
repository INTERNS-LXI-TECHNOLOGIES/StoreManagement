package com.lxisoft.store.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lxisoft.store.domain.Sale} entity.
 */
public class SaleDTO implements Serializable {

    private Long id;

    private Long noOfProduct;

    private Instant date;

    private Double amount;

    private Long unitCost;

    private String productName;


    private Long customerId;

    private Long productId;

    private Long storeId;

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

    public Long getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Long unitCost) {
        this.unitCost = unitCost;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SaleDTO saleDTO = (SaleDTO) o;
        if (saleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleDTO{" +
            "id=" + getId() +
            ", noOfProduct=" + getNoOfProduct() +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", unitCost=" + getUnitCost() +
            ", productName='" + getProductName() + "'" +
            ", customerId=" + getCustomerId() +
            ", productId=" + getProductId() +
            ", storeId=" + getStoreId() +
            "}";
    }
}
