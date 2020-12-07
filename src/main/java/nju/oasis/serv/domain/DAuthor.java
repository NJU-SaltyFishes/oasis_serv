package nju.oasis.serv.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@QueryResult
@Data
@NoArgsConstructor
public class DAuthor {

    private int distance;

    private List<Author> authors;

    public DAuthor(int distance){
        this.distance = distance;
        this.authors = new ArrayList<>();
    }
}
