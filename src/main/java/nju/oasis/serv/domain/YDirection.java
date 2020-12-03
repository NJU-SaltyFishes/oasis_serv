package nju.oasis.serv.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class YDirection {


    private int year;

    private String directions;

    private List<Direction>formatDirections;
}
