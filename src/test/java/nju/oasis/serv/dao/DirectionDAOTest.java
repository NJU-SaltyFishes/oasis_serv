package nju.oasis.serv.dao;

import nju.oasis.serv.OasisServApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectionDAOTest extends OasisServApplicationTests {

    @Resource
    DirectionDAO directionDAO;

    @Test
    void getDirectionsByPrefix() {
        String prefix = "ani";
        List<String>directions = directionDAO.getDirectionsByPrefix(prefix);
        assertNotNull(directions);
        assertEquals(7,directions.size());
        assertEquals("Animachine renderer",directions.get(0));
    }

    @Test
    void getDirectionsByPrefix1() {
        String prefix = "grlo";
        List<String>directions = directionDAO.getDirectionsByPrefix(prefix);
        assertNotNull(directions);
        assertEquals(0,directions.size());
    }
}