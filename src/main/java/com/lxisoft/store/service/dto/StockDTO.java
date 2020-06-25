package com.lxisoft.store.service.dto;
import java.io.Serializable;
import java.util.Objects;

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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockDTO stockDTO = (StockDTO) o;
        if (stockDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", noOfItem=" + getNoOfItem() +
            "}";
    }
}
