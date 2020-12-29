package nju.oasis.serv.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReaderRecommendForm {

    private String title;

    @JsonProperty("abstract")
    private String abstractContent;

    private List<String>directions;

    private List<String>publications;

    private int limit;

    private int sort;
}
