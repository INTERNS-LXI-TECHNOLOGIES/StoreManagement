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

    @Column(name = "brand")
    private String brand;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "no_of_stock")
    private Long noOfStock;

    @Column(name = "manufacturing_date")
    private Instant manufacturingDate;

    @Column(name = "expiring_date")
    private Instant expiringDate;

    @Column(name = "warranty")
    private String warranty;

    @Column(name = "image_link")
    private String imageLink;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToMany(mappedBy = "product")
    private Set<Cart> carts = new HashSet<>();

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

    public String getBrand() {
        return brand;
    }

    public Product brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public Long getNoOfStock() {
        return noOfStock;
    }

    public Product noOfStock(Long noOfStock) {
        this.noOfStock = noOfStock;
        return this;
    }

    public void setNoOfStock(Long noOfStock) {
        this.noOfStock = noOfStock;
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

    public String getWarranty() {
        return warranty;
    }

    public Product warranty(String warranty) {
        this.warranty = warranty;
        return this;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getImageLink() {
        return imageLink;
    }

    public Product imageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public byte[] getImage() {
        return image;
    }

    public Product image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Product imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public Product carts(Set<Cart> carts) {
        this.carts = carts;
        return this;
    }

    public Product addCart(Cart cart) {
        this.carts.add(cart);
        cart.setProduct(this);
        return this;
    }

    public Product removeCart(Cart cart) {
        this.carts.remove(cart);
        cart.setProduct(null);
        return this;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
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
            ", brand='" + getBrand() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", price=" + getPrice() +
            ", noOfStock=" + getNoOfStock() +
            ", manufacturingDate='" + getManufacturingDate() + "'" +
            ", expiringDate='" + getExpiringDate() + "'" +
            ", warranty='" + getWarranty() + "'" +
            ", imageLink='" + getImageLink() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
