package nju.oasis.serv.dao;

import nju.oasis.serv.OasisServApplicationTests;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PublicationDAOTest extends OasisServApplicationTests {

    @Resource
    PublicationDAO publicationDAO;

    @Test
    void getPublicationsByPrefix() {
        String prefix = "acm";
        List<String> directions = publicationDAO.getPublicationsByPrefix(prefix);
        assertNotNull(directions);
        assertEquals(7,directions.size());
        assertEquals("ACM Computing Surveys",directions.get(1));
    }

    @Test
    void getDirectionsByPrefix1() {
        String prefix = "grlo";
        List<String>directions = publicationDAO.getPublicationsByPrefix(prefix);
        assertNotNull(directions);
        assertEquals(0,directions.size());
    }
}