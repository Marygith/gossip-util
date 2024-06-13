package gossip_utils.service;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class HttpService {

    private final static RestTemplate restTemplate = new RestTemplate();


    public static void send(HttpEntity entity, String url) {
            restTemplate.postForObject(url, entity, Void.class);
    }
}
