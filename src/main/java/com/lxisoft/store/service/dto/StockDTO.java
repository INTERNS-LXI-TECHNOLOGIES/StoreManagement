package com.lxisoft.store.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.lxisoft.store.domain.Stock} entity.
 */
public class StockDTO implements Serializable {
    
    private Long id;

    private String description;

    private Long noOfItem;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNoOfItem() {
        return noOfItem;
    }

    public void setNoOfItem(Long noOfItem) {
        this.noOfItem = noOfItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockDTO)) {
            return false;
        }

        return id != null && id.equals(((StockDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", noOfItem=" + getNoOfItem() +
            "}";
    }
}
