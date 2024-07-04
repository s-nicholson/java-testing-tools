package com.example.testing.wiremock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiClientTest {
    /**
     * We could try to test our ApiClient by isolating it using a mocked http client.
     * This is likely a bad idea as it involves mocking a complicated class which will
     * make our test brittle.
     * Also, if our understanding of the behaviour of HttpClient is wrong, then we're
     * going to have a bad time.
     */
    @Nested
    class WithoutWiremock {
        @Test
        @SneakyThrows
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
            var mockResponse = mock(HttpResponse.class);
            when(mockResponse.body()).thenReturn(response);
            var mockClient = mock(HttpClient.class);
            when(mockClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                    .thenReturn(mockResponse);

            ApiClient apiClient = new ApiClient(
                    "http://localhost:8080", mockClient);

            var actual = apiClient.getItems();

            assertThat(actual).isEqualTo(expected);
        }
    }

    /**
     * This test class spins up wiremock as a local webserver so we can test our code with real HTTP calls.
     * This saves us the pain of trying to mock out the HTTP client and also gives us real confidence that
     * we have a working client.
     */
    @Nested
    @WireMockTest(httpPort = 8080)
    class WithWiremock {
        private final ApiClient apiClient = new ApiClient(
                "http://localhost:8080", HttpClient.newHttpClient());

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
                    .willReturn(aResponse().withStatus(400)));

            var thrown = assertThrows(RuntimeException.class, () -> apiClient.putItem(new ApiClient.Item("a")));

            assertThat(thrown.getMessage()).isEqualTo("Oh no");
        }
    }
}