package com.example.testing.wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WireMockTest(httpPort = 8080)
class ApiClientTest {
    private final ApiClient apiClient = new ApiClient("http://localhost:8080");

    @Test
    void can_get_items() {
        var expected = List.of(
                new ApiClient.Item("a"),
                new ApiClient.Item("b"),
                new ApiClient.Item("c")
        );
        String response = """
                [
                    { "id": "a" },
                    { "id": "b" },
                    { "id": "c" }
                ]
                """.trim();
        stubFor(get(urlEqualTo("/items"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)));

        var actual = apiClient.getItems();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void can_put_an_item() {
        stubFor(put(urlEqualTo("/item"))
                .withRequestBody(equalToJson("{ \"id\": \"a\" }"))
                .willReturn(aResponse().withStatus(200)));

        apiClient.putItem(new ApiClient.Item("a"));

        verify(putRequestedFor(urlEqualTo("/item"))
                .withRequestBody(equalToJson("{ \"id\": \"a\" }")));
    }

    @Test
    void put_item_failure_throws() {
        stubFor(put(urlEqualTo("/item"))
                .withRequestBody(equalToJson("{ \"id\": \"a\" }"))
                .willReturn(aResponse().withStatus(400)));

        var thrown = assertThrows(RuntimeException.class, () -> apiClient.putItem(new ApiClient.Item("a")));

        assertThat(thrown.getMessage()).isEqualTo("Oh no");
    }
}