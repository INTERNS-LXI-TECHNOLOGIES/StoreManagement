package com.lxisoft.store.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Store.
 */
@Entity
@Table(name = "store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "store")
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "store")
    private Set<Customer> customers = new HashSet<>();

    @OneToMany(mappedBy = "store")
    private Set<Category> categories = new HashSet<>();

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

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Store description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Store phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Store products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Store addProduct(Product product) {
        this.products.add(product);
        product.setStore(this);
        return this;
    }

    public Store removeProduct(Product product) {
        this.products.remove(product);
        product.setStore(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public Store customers(Set<Customer> customers) {
        this.customers = customers;
        return this;
    }

    public Store addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setStore(this);
        return this;
    }

    public Store removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setStore(null);
        return this;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Store categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Store addCategory(Category category) {
        this.categories.add(category);
        category.setStore(this);
        return this;
    }

    public Store removeCategory(Category category) {
        this.categories.remove(category);
        category.setStore(null);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        return id != null && id.equals(((Store) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Store{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
