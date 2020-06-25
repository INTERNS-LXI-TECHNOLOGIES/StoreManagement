package com.lxisoft.store.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Stock.
 */
@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "no_of_item")
    private Long noOfItem;

    @OneToMany(mappedBy = "stock")
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "stock")
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Stock description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNoOfItem() {
        return noOfItem;
    }

    public Stock noOfItem(Long noOfItem) {
        this.noOfItem = noOfItem;
        return this;
    }

    public void setNoOfItem(Long noOfItem) {
        this.noOfItem = noOfItem;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Stock categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Stock addCategory(Category category) {
        this.categories.add(category);
        category.setStock(this);
        return this;
    }

    public Stock removeCategory(Category category) {
        this.categories.remove(category);
        category.setStock(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Stock products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Stock addProduct(Product product) {
        this.products.add(product);
        product.setStock(this);
        return this;
    }

    public Stock removeProduct(Product product) {
        this.products.remove(product);
        product.setStock(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock)) {
            return false;
        }
        return id != null && id.equals(((Stock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", noOfItem=" + getNoOfItem() +
            "}";
    }
}
