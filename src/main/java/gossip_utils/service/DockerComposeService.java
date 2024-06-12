package gossip_utils.service;

public class DockerComposeService {
    public void init(int nodesAmount) {

        for (int i = 0; i < Math.min(nodesAmount, 10); i++) {
            System.out.println("  gossip-" + i + ":\n" +
                    "    image: gossip-image\n" +
                    "    ports:\n" +
                    "      - \"820" + i + ":8080\"" + "\n" +
                    "    networks:\n" +
                    "      - gossip-network" + "\n" +
                    "    volumes:\n" +
                    "      - D:\\dev\\gossip\\" + i + ":/opt/app/data");
        }
        if (nodesAmount > 10) {
            for (int i = 10; i < nodesAmount; i++) {
                System.out.println("  gossip-" + i + ":\n" +
                        "    image: gossip-image\n" +
                        "    ports:\n" +
                        "      - \"82" + i + ":8080\"" + "\n" +
                        "    networks:\n" +
                        "      - gossip-network" + "\n" +
                        "    volumes:\n" +
                        "      - D:\\dev\\gossip\\" + i + ":/opt/app/data");
            }
        }
    }
}
