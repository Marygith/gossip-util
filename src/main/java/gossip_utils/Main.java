package gossip_utils;


import gossip_utils.service.InitScriptService;
import gossip_utils.service.MessageInitService;
import gossip_utils.service.StatisticsService;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int nodesAmount = 50;
        int neighboursAmount = 50;

        new InitScriptService().init(nodesAmount, neighboursAmount);


        Thread.sleep(2000);
        new MessageInitService().initAndGetFilenames(nodesAmount, neighboursAmount);

        List<String> fileNames = List.of(
                "1_50_10_0.0",
                "2_50_10_0.1",
                "3_50_10_0.25",
                "4_50_10_0.5"
        );

        new StatisticsService().gatherStatisticsByFileName(fileNames, nodesAmount);
//        new StatisticsService().gatherStatisticsByFileName(List.of("1_35_10_0.0"), nodesAmount);
    }
}
