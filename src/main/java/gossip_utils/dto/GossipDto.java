package gossip_utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GossipDto {

    private Gossip gossip;
    private int neighboursCount;
    private float lossRate;

}
