package nju.oasis.serv.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@NodeEntity("Author")
public class Author {

    @Property("authorId")
    private long authorId;

    @Property("name")
    private String name;
}
