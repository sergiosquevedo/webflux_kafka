package org.sergio.products_shipping.repository.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "shipping")
public class ShippingEntity {
    @Id
    @Column("id_shipping")
    private UUID idShipping;
    private String product;
    @Column("date_shipping")
    private LocalDateTime dateShipping;
    private String address;
    private String status;

    public UUID getIdShipping() {
        return idShipping;
    }
    public void setIdShipping(UUID idShipping) {
        this.idShipping = idShipping;
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
