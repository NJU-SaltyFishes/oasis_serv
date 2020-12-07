package nju.oasis.serv.dao;

import nju.oasis.serv.OasisServApplicationTests;
import nju.oasis.serv.domain.DAuthor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorNeo4jDAOTest extends OasisServApplicationTests {

    @Autowired
    AuthorNeo4jDAO authorNeo4jDAO;

    @Test
    void findByAuthorId() {
        List<DAuthor>dAuthors = authorNeo4jDAO.findByAuthorId(7209,1,2,15);
        assertEquals(2,dAuthors.size());
        assertEquals(15,dAuthors.get(0).getAuthors().size());
        assertEquals(15,dAuthors.get(1).getAuthors().size());
    }

    @Test
    void findByAuthorId1(){
        List<DAuthor>dAuthors = authorNeo4jDAO.findByAuthorId(1000000000,1,1,15);
        assertEquals(0,dAuthors.size());
    }
}