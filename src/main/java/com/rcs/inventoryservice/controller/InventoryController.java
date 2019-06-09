package com.rcs.inventoryservice.controller;

import com.rcs.inventoryservice.model.ItemInventory;
import com.rcs.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * GET /inventory/{storeId}/{sku} -> curl -Lik http://localhost:8080/inventory/1/2
 * GET /inventory/{storeId} (optional)
 * POST /inventory/{storeId}/order/{sku} ->
 * GET /inventory/{storeId}/anticipated/{sku} -> curl -Lik http://localhost:8080/inventory/1/anticipated/2
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

    @PostMapping("/{storeId}/order/{sku}")
    public ResponseEntity<String> placeOrder(@PathVariable("storeId") Long storeId, @PathVariable("sku") Long sku) {
        return ResponseEntity.ok(inventoryService.placeOrder(storeId, sku));
    }

    //uncomment me
    @GetMapping(value = "/{storeId}/anticipated/{sku}")
    public ResponseEntity<ItemInventory> getAnticipatedSkuInventoryForBranch(
            @PathVariable("storeId") Long storeId, @PathVariable("sku") Long sku) {
        return ResponseEntity.ok(inventoryService.getAnticipatedSkuInventoryForBranch(storeId, sku));
    }
}
