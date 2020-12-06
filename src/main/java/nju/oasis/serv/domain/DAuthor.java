package nju.oasis.serv.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;
import java.util.Set;

@QueryResult
@Data
public class DAuthor {

    private int distance;

    private List<Author> authors;
}
