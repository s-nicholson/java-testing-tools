package com.example.testing.wiremock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiClient {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl;
    private final HttpClient client;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        client = HttpClient.newHttpClient();
    }

    @SneakyThrows
    public List<Item> getItems() {
        var request = HttpRequest.newBuilder()
                .uri(new URI("%s/items".formatted(baseUrl)))
                .GET()
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), new TypeReference<>() {
        });
    }

    @SneakyThrows
    public void putItem(Item item) {
        var request = HttpRequest.newBuilder()
                .uri(new URI("%s/item".formatted(baseUrl)))
                .PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(item)))
                .build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Oh no");
        }
    }

    public record Item(
            @JsonProperty
            String id
    ) {
    }
}
