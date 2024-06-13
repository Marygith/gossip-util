package gossip_utils.service;

import java.util.*;

public class NeighboursSchemaService {

    public Map<String, Set<String>> assignNeighbours(List<String> urls, int neighboursCount) {
        List<String> assignList = new ArrayList<>();
        Random random = new Random();

        Map<String, Set<String>> urlToNeighbours = new HashMap<>();
        if (neighboursCount == urls.size()) {
            for(String url: urls) {
                urlToNeighbours.put(url, new HashSet<>(urls));
            }
            return urlToNeighbours;
        }
        if (neighboursCount > urls.size() / 2) {
            assignList = new ArrayList<>(urls);
            for(String url: urls) {
                Collections.shuffle(assignList);
                urlToNeighbours.put(url, new HashSet<>(assignList.subList(0, neighboursCount)));
            }
        }
        for (int i = 0; i < neighboursCount; i++) {
            assignList.addAll(urls);
        }
        for (String url : urls) {

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
                }
                if (neighbour.equals(url)) break;
                neighbours.add(neighbour);
            }
            neighbours.forEach(assignList::remove);
            urlToNeighbours.put(url, neighbours);
        }
        return urlToNeighbours;
    }
}
