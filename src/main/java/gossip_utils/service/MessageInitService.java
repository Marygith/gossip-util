package gossip_utils.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gossip_utils.dto.Gossip;
import gossip_utils.dto.GossipDto;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Slf4j
public class MessageInitService {

    public List<String> initAndGetFilenames(int nodesAmount, int neighboursAmount) {

        List<String> fileNames = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < nodesAmount; i++) {
            urls.add(String.valueOf(i));
        }

        List<Integer> neighbourCounts = new ArrayList<>(Set.of(/*1,*/ neighboursAmount/5/*, neighboursAmount/2*/));
        List<Float> lossRates = List.of(0.0F/*, 0.1F, 0.25F, 0.5F*/);

        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();

        try {
            long hash = 22;
            for (int neighbourCountInd = 0; neighbourCountInd < neighbourCounts.size(); neighbourCountInd++) {
                for (int lossRateInd = 0; lossRateInd < lossRates.size(); lossRateInd++) {
                    hash++;
                    GossipDto gossipDto = new GossipDto(new Gossip("Message " + neighbourCountInd + lossRateInd, hash), neighbourCounts.get(neighbourCountInd), lossRates.get(lossRateInd));
                    String url = urls.get(new Random().nextInt( urls.size()));
                    if (url.length() == 1) url = "0" + url;
                    HttpService.sendPostRequest(client, "http://localhost:82" + url + "/push", objectMapper.writeValueAsString(gossipDto));
                    System.out.println("Sending gossip " + gossipDto + " to " + "http://localhost:82" + url);
                    log.info(StatisticsService.renderFileName(lossRates.get(lossRateInd), neighbourCounts.get(neighbourCountInd), neighboursAmount, hash));
                    fileNames.add(StatisticsService.renderFileName(lossRates.get(lossRateInd), neighbourCounts.get(neighbourCountInd), neighboursAmount, hash));
                }
            }
        } catch (Exception e) {
            log.error("ooooops");
        } finally {
//            client.close();
        }
        return fileNames;
    }
}
