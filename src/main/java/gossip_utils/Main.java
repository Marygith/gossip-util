package gossip_utils;


import gossip_utils.service.InitScriptService;
import gossip_utils.service.MessageInitService;
import gossip_utils.service.StatisticsService;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Lets start");
        int nodesAmount = 50;
        int neighboursCount = 40;
        //assign neighbours to each server
        new InitScriptService().init(nodesAmount, neighboursCount);

        Thread.sleep(5000);

//        init message sending
        List<String> fileNames = new MessageInitService().initAndGetFilenames(nodesAmount, neighboursCount);
        Thread.sleep(5000);
        //generate services for docker-compose file
//        new DockerComposeService().init(50);

//        gather statistics
        new StatisticsService().gatherStatisticsByFileName(fileNames, nodesAmount);
    }
}
