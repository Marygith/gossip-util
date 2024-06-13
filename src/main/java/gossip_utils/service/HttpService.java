package gossip_utils.service;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HttpService {

    private final static RestTemplate restTemplate = new RestTemplate();
    public static void sendPostRequest(HttpClient client, String url, String requestBody) {
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
//                .header( "Accept", "application/json" )
                .POST(body)
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void send(HttpEntity entity, String url) {

            restTemplate.postForObject(url, entity, Void.class);

    }
}
