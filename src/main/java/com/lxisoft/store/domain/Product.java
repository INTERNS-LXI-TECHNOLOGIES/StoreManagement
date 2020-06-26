package com.lxisoft.store.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "manufacturing_date")
    private Instant manufacturingDate;

    @Column(name = "expiring_date")
    private Instant expiringDate;

    @Column(name = "warrenty")
    private String warrenty;

    @OneToOne
    @JoinColumn(unique = true)
    private Stock stock;

    @OneToMany(mappedBy = "product")
    private Set<Sale> sales = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Store store;

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public Product quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getManufacturingDate() {
        return manufacturingDate;
    }

    public Product manufacturingDate(Instant manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
        return this;
    }

    public void setManufacturingDate(Instant manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public Instant getExpiringDate() {
        return expiringDate;
    }

    public Product expiringDate(Instant expiringDate) {
        this.expiringDate = expiringDate;
        return this;
    }

    public void setExpiringDate(Instant expiringDate) {
        this.expiringDate = expiringDate;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public Product warrenty(String warrenty) {
        this.warrenty = warrenty;
        return this;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }

    public Stock getStock() {
        return stock;
    }

    public Product stock(Stock stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public Product sales(Set<Sale> sales) {
        this.sales = sales;
        return this;
    }

    public Product addSale(Sale sale) {
        this.sales.add(sale);
        sale.setProduct(this);
        return this;
    }

    public Product removeSale(Sale sale) {
        this.sales.remove(sale);
        sale.setProduct(null);
        return this;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    public Store getStore() {
        return store;
    }

    public Product store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", price=" + getPrice() +
            ", manufacturingDate='" + getManufacturingDate() + "'" +
            ", expiringDate='" + getExpiringDate() + "'" +
            ", warrenty='" + getWarrenty() + "'" +
            "}";
    }
}
