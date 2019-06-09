package com.rcs.inventoryservice.client;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import com.rcs.inventoryservice.model.ItemInventory;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

//@ExtendWith({SpringExtension.class,PactConsumerTestExt.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = "inventoryPredictionProvider.base-url:http://localhost:9090",
    classes = {InventoryPredictionServiceClient.class, RestTemplate.class})
@PactTestFor(providerName = "inventoryPredictionProvider", port = "9090")
@ExtendWith({PactConsumerTestExt.class})

public class InventoryPredictionServiceClientTest {

  @Autowired
  private InventoryPredictionServiceClient inventoryPredictionServiceClient;

  @Pact(provider="inventoryPredictionProvider",consumer = "inventory-service")
  public RequestResponsePact pactUserExists(PactDslWithProvider builder) {
    return builder.given("Inventory for Sku 2 in store 1 exists")
        .uponReceiving("A request to /prediction/1/2")
        .path("/prediction/1/2")
        .method("GET")
        .willRespondWith()
        .status(200)
        .body(LambdaDsl.newJsonBody((o) -> o
            .stringType("quantity", "1")
     ).build())
    .toPact();
  }


  @PactVerification(fragment = "inventoryExists")
  @Test
  public void inventoryExists(MockServer mockServer) {
    // when
    ResponseEntity<ItemInventory> response = new RestTemplate()
        .getForEntity(mockServer.getUrl() + "/prediction/1/2", ItemInventory.class);

    // then
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    assertThat(response.getBody().getQuantity()).isEqualTo(1);

    final ItemInventory inventory = inventoryPredictionServiceClient.getPredictedInventory();
    assertThat(inventory.getQuantity()).isEqualTo(1);
  }

}
