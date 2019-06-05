package com.rcs.inventoryservice.controller;

import com.rcs.inventoryservice.model.ItemInventory;
import com.rcs.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * GET /inventory/{storeId}/{sku}
 * GET /inventory/{storeId} (optional)
 * POST /inventory/{storeId}/order/{sku}
 * GET /inventory/{storeId}/anticipated/{sku}
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping(value = "/{storeId}/{sku}")
    public ResponseEntity<ItemInventory> getSkuInventoryForBranch(
            @PathVariable("storeId") Long storeId, @PathVariable("sku") Long sku) {
        return ResponseEntity.ok(inventoryService.getSkuInventoryForBranch(storeId, sku));
    }


}
