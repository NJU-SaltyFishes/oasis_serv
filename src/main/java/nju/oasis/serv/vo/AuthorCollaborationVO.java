package nju.oasis.serv.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import nju.oasis.serv.domain.AuthorCollaboration;

import java.util.List;

@Data
@NoArgsConstructor
public class AuthorCollaborationVO {
    private long startId;

    private long endId;

    private double distance;

    private List<String> directions;

    public AuthorCollaborationVO(AuthorCollaboration authorCollaboration){
        this.startId = authorCollaboration.getStartId();
        this.endId = authorCollaboration.getEndId();
        this.distance = authorCollaboration.getDistance();
        this.directions = authorCollaboration.getDirectionList();
    }
}
