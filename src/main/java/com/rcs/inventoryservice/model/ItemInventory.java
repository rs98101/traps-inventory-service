package com.rcs.inventoryservice.model;

public class ItemInventory {
    private Long sku;
    private String description;
    private int quantity;

    public ItemInventory() {}
    public ItemInventory(Long sku, String description, int quantity) {
        this.sku = sku;
        this.description = description;
        this.quantity = quantity;
    }

    public Long getSku() {
        return sku;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
