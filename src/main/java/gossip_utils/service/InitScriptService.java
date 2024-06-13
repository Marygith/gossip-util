package gossip_utils.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gossip_utils.dto.NeighboursDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class InitScriptService {
    public void init(int nodesAmount, int neighboursCount) {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < nodesAmount; i++) {
            urls.add(String.valueOf(i));
            File file = new File("D:\\dev\\gossip\\" + i);
            file.mkdirs();
        }

        System.out.println("finished creating neighbours");
        var urlToNeighbours = new NeighboursSchemaService().assignNeighbours(urls, neighboursCount);
        log.info("Created neighbours schema");
        HttpClient client = HttpClient.newHttpClient();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            for (Map.Entry<String, Set<String>> entry : urlToNeighbours.entrySet()) {

                NeighboursDto neighboursDto = new NeighboursDto(entry.getValue());
                log.info("Sending to url {} following neighbours: {}", entry.getKey(), neighboursDto.getUrls());
                var url = entry.getKey();
                if (entry.getKey().length() == 1) url = "0" + url;
                System.out.println("sending to " + "http://localhost:82" + url + "/neighbours");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<NeighboursDto> entity = new HttpEntity<>(neighboursDto, headers);
                HttpService.send(entity, "http://localhost:82" + url + "/neighbours");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
//            client.close();
        }


    }
}
