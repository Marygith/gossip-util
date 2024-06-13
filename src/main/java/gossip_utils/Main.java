package gossip_utils;


import gossip_utils.service.InitScriptService;
import gossip_utils.service.MessageInitService;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int nodesAmount = 50;
        int neighboursCount = 50;
        new InitScriptService().init(nodesAmount, neighboursCount);


        Thread.sleep(2000);
        new MessageInitService().initAndGetFilenames(nodesAmount, neighboursCount);

//        new DockerComposeService().init(100);
        List<String> fileNames = List.of(
                "25_50_25_0.0",
                "26_50_10_0.0",
                "28_50_5_0.0",
                "27_50_1_0.0");

//        new StatisticsService().gatherStatisticsByFileName(List.of("1_50_5_0.0"), nodesAmount);
    }
}
