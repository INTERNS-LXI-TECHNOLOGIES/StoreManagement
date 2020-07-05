package com.lxisoft.store.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Sale.
 */
@Entity
@Table(name = "sale")
public class Sale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no_of_product")
    private Long noOfProduct;

    @Column(name = "date")
    private Instant date;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "unit_cost")
    private Long unitCost;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JsonIgnoreProperties("sales")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("sales")
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties("sales")
    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoOfProduct() {
        return noOfProduct;
    }

    public Sale noOfProduct(Long noOfProduct) {
        this.noOfProduct = noOfProduct;
        return this;
    }

    public void setNoOfProduct(Long noOfProduct) {
        this.noOfProduct = noOfProduct;
    }

    public Instant getDate() {
        return date;
    }

    public Sale date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public Sale amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getUnitCost() {
        return unitCost;
    }

    public Sale unitCost(Long unitCost) {
        this.unitCost = unitCost;
        return this;
    }

    public void setUnitCost(Long unitCost) {
        this.unitCost = unitCost;
    }

    public String getProductName() {
        return productName;
    }

    public Sale productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Sale customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public Sale product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public Sale store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sale)) {
            return false;
        }
        return id != null && id.equals(((Sale) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sale{" +
            "id=" + getId() +
            ", noOfProduct=" + getNoOfProduct() +
            ", date='" + getDate() + "'" +
            ", amount=" + getAmount() +
            ", unitCost=" + getUnitCost() +
            ", productName='" + getProductName() + "'" +
            "}";
    }
}
