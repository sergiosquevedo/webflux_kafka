package org.sergio.products_orders.model;

public class ProductOrder {
    private int code;
    private String name;
    private int units;
    private String address;
    
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
    public int getUnits() {
        return units;
    }
    public void setUnits(int units) {
        this.units = units;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }    
}
