package nju.oasis.serv.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AuthorCollaboration {

    private long startId;

    private long endId;

    private String endName;

    private double distance;

    private String directions;

    private List<String>directionList;
}
