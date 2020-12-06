package nju.oasis.serv.dao;

import nju.oasis.serv.domain.Author;
import nju.oasis.serv.domain.DAuthor;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface AuthorNeo4jDAO extends CrudRepository<Author,Long> {

    @Query("MATCH (a1:Author{authorId:$0})\n" +
            "CALL apoc.path.spanningTree(a1, {minLevel:1,maxLevel:2}) YIELD path\n" +
            "WITH last(nodes(path)) as p,length(path) as numberOfHops\n" +
            "order by p.authorId\n" +
            "WITH numberOfHops, collect(p) as nodes\n" +
            "RETURN numberOfHops as distance,nodes[0..15] as authors\n" +
            "order by distance")
    List<DAuthor> findByAuthorId(int authorId);
}
