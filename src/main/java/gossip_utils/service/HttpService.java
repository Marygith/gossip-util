package gossip_utils.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HttpService {

    public static void sendPostRequest(HttpClient client, String url, String requestBody) {
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .header( "Accept", "application/json" )
                .POST(body)
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
