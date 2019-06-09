package com.rcs.inventoryservice.client;

import com.rcs.inventoryservice.model.ItemInventory;
import com.rcs.inventoryservice.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryPredictionServiceClient {

  private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

  @Autowired
  private RestTemplate restTemplate;

  public ItemInventory getPredictedInventory(Long storeId, Long sku) {
    ResponseEntity<ItemInventory> itemInventory =
        restTemplate.getForEntity(
                "/prediction/" + String.valueOf(storeId) + "/" + String.valueOf(sku),
                ItemInventory.class);
    ItemInventory inventory = itemInventory.getBody();
    logger.info("Predicted inventory quantity: " + inventory.getQuantity());
    return inventory;
  }

}
