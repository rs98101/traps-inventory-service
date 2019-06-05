package com.rcs.inventoryservice.model;

public class Store {
    private Long storeId;
    private String name;

    public Store(Long storeId, String name) {
        this.storeId = storeId;
        this.name = name;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
