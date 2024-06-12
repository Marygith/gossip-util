package gossip_utils.service;

import java.util.*;

public class NeighboursSchemaService {

    public Map<String, Set<String>> assignNeighbours(List<String> urls, int neighboursCount) {
        List<String> assignList = new ArrayList<>();
        Random random = new Random();


        for (int i = 0; i < neighboursCount; i++) {
            assignList.addAll(urls);
        }
//        System.out.println("assign list: " + assignList);
        Map<String, Set<String>> urlToNeighbours = new HashMap<>();
        for (String url : urls) {
//            System.out.println("url: " + url);
//            System.out.println("assign list: " + assignList);

            Set<String> neighbours = new HashSet<>();
            while (neighbours.size() < neighboursCount) {
                if (url.equals(urls.get(urls.size() - 1))) {
                    neighbours.addAll(assignList);
                    break;
                }
                String neighbour = assignList.get(random.nextInt(assignList.size()));
                while (neighbour.equals(url)) {
                    neighbour = assignList.get(random.nextInt(assignList.size()));
                    if (assignList.stream().allMatch(n -> n.equals(url))) break;
//                    System.out.println("a");
                }
                if (neighbour.equals(url)) break;
                neighbours.add(neighbour);
//                System.out.println("b");
            }
            neighbours.forEach(assignList::remove);
            urlToNeighbours.put(url, neighbours);
//            System.out.println("added " + neighbours + " to url " + url);
        }
        return urlToNeighbours;
    }
}
