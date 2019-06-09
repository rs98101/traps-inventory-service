package com.rcs.inventoryservice.client;

import static jdk.nashorn.internal.objects.Global.println;

import com.rcs.inventoryservice.model.ItemInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryPredictionServiceClient {

  @Autowired
  private RestTemplate restTemplate;

  public ItemInventory getPredictedInventory() {
    ResponseEntity<ItemInventory> itemInventory =
        restTemplate.getForEntity("http://localhost:8081/prediction/1/2",ItemInventory.class);
    ItemInventory inventory = itemInventory.getBody();
    System.out.println(inventory.getQuantity());
    return inventory;
  }

}
