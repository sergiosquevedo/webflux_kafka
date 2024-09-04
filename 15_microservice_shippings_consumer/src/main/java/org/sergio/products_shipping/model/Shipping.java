package org.sergio.products_shipping.model;

import java.time.LocalDateTime;

public class Shipping {
    private String id;
    private String product;
    private LocalDateTime dateShipping;
    private String address;
    private String status;
    
    public Shipping(){

    }

    public Shipping(String id, String product, LocalDateTime dateShipping, String address, String status) {
        this.id = id;
        this.product = product;
        this.dateShipping = dateShipping;
        this.address = address;
        this.status = status;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public LocalDateTime getDateShipping() {
        return dateShipping;
    }
    public void setDateShipping(LocalDateTime dateShipping) {
        this.dateShipping = dateShipping;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
