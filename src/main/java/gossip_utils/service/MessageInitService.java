package gossip_utils.service;

import gossip_utils.dto.Gossip;
import gossip_utils.dto.GossipDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Slf4j
public class MessageInitService {
    //third change
    public void initAndGetFilenames(int nodesAmount, int neighboursAmount) {
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < nodesAmount; i++) {
            urls.add(String.valueOf(i));
        }

        List<Integer> neighbourCounts = new ArrayList<>(Set.of(10));
        List<Float> lossRates = List.of(0.0F, 0.1F, 0.25F, 0.5F);

        try {
            long hash = 0;
            for (int neighbourCountInd = 0; neighbourCountInd < neighbourCounts.size(); neighbourCountInd++) {
                for (int lossRateInd = 0; lossRateInd < lossRates.size(); lossRateInd++) {
                    hash++;
                    GossipDto gossipDto = new GossipDto(new Gossip("Message " + neighbourCountInd + lossRateInd, hash), neighbourCounts.get(neighbourCountInd), lossRates.get(lossRateInd));
                    String url = urls.get(new Random().nextInt( urls.size()));
                    if (url.length() == 1) url = "0" + url;
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<GossipDto> entity = new HttpEntity<>(gossipDto, headers);
                    HttpService.send(entity, "http://localhost:82" + url + "/push");
                    System.out.println("Sending gossip " + gossipDto + " to " + "http://localhost:82" + url);
                    log.info(StatisticsService.renderFileName(lossRates.get(lossRateInd), neighbourCounts.get(neighbourCountInd), neighboursAmount, hash));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("ooooops");
        }
    }
}
