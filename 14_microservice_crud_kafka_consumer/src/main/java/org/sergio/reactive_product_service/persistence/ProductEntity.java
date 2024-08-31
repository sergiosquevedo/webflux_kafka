package org.sergio.reactive_product_service.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "Products")
public class ProductEntity implements Persistable<Integer> {
    @Id
    private int code;
    private String name;
    private String category;
    private double price;
    private int stock;
    @Transient
    private boolean newProduct;

    public ProductEntity() {
    }

    public ProductEntity(boolean isNewProduct, int code, String name, String category, double price, int stock) {
            this.code = code;
            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
            this.newProduct = isNewProduct;
    }

    public ProductEntity(int code, String name, String category, double price, int stock) {
        this(true, code, name, category, price, stock);
    }

    @Override
    public Integer getId() {
        return code;
    }

    @Override
    public boolean isNew() {
        return newProduct;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double prize) {
        this.price = prize;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
