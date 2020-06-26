package com.lxisoft.store.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lxisoft.store.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String quantity;

    private Double price;

    private Instant manufacturingDate;

    private Instant expiringDate;

    private String warrenty;


    private Long stockId;

    private Long storeId;

    private Long categoryId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(Instant manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Instant getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Instant expiringDate) {
        this.expiringDate = expiringDate;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (productDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", price=" + getPrice() +
            ", manufacturingDate='" + getManufacturingDate() + "'" +
            ", expiringDate='" + getExpiringDate() + "'" +
            ", warrenty='" + getWarrenty() + "'" +
            ", stockId=" + getStockId() +
            ", storeId=" + getStoreId() +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
