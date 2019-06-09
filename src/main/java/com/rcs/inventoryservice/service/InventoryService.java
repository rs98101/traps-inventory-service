package com.rcs.inventoryservice.service;

import com.rcs.inventoryservice.client.InventoryPredictionServiceClient;
import com.rcs.inventoryservice.model.ItemInventory;
import com.rcs.inventoryservice.model.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class InventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryPredictionServiceClient inventoryPredictionServiceClient;
    private Map<Long, Store> stores;
    private Map<Store, Map<Long, ItemInventory>> itemsByStore;

    public ItemInventory getSkuInventoryForBranch(Long storeId, Long sku) {
        checkStoreAndSku(storeId, sku);
        inventoryPredictionServiceClient.getPredictedInventory();
        Store store = stores.get(storeId);
        return itemsByStore.get(store).get(sku);
    }

    public String placeOrder(Long storeId, Long sku) {
        checkStoreAndSku(storeId, sku);
        Store store = stores.get(storeId);
        ItemInventory item = itemsByStore.get(store).get(sku);
        return "Ordered 100 more " + item.getDescription() + "s";
    }

    private void checkStoreAndSku(Long storeId, Long sku) {
        if (!stores.containsKey(storeId)) throw new RuntimeException("Unknown store: " + storeId);

        Store store = stores.get(storeId);

        if (!itemsByStore.get(store).containsKey(sku)) throw new RuntimeException("Unknown sku: " + sku);
    }

    @PostConstruct
    private void initializeInventory() {
        stores = new HashMap<>();
        itemsByStore = new HashMap<>();

        //create stores with inventory
        for (int i = 1; i <= 10; i++) {
            Store store = new Store(Long.valueOf(i), "Store #" + i);
            stores.put(store.getStoreId(), store);

            //create some items for the store
            ItemInventory umbrellas =
                    new ItemInventory(1L, "Umbrella", ThreadLocalRandom.current().nextInt(0, 100));
            ItemInventory shovels =
                    new ItemInventory(2L, "Shovel", ThreadLocalRandom.current().nextInt(0, 100));
            Map<Long, ItemInventory> itemInventoryMap = new HashMap<>();
            itemInventoryMap.put(umbrellas.getSku(), umbrellas);
            itemInventoryMap.put(shovels.getSku(), shovels);

            itemsByStore.put(store, itemInventoryMap);
        }
        logger.info("Finished initializing stores.");
    }
}
