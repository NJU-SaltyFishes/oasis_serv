package nju.oasis.serv.dao;

import nju.oasis.serv.OasisServApplicationTests;
import nju.oasis.serv.domain.AuthorCollaboration;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorCollaborationDAOTest extends OasisServApplicationTests {

    @Resource
    AuthorCollaborationDAO authorCollaborationDAO;

    @Test
    void findAuthorCollaborationByAuthorId() {

        long startId = 1;
        long endId = 42407;
        String endName = "Artūras Serackis";
        double distance = 0.7;
        String directions = "[\"dynamic time warping\", \"search optimisation\", \"Speech recognition\"]";

        List<AuthorCollaboration> authorCollaborations =
                authorCollaborationDAO.findAuthorCollaborationByAuthorId(startId,0.7,0.8,15);
        assertNotNull(authorCollaborations);
        assertEquals(1,authorCollaborations.size());
        AuthorCollaboration authorCollaboration = authorCollaborations.get(0);
        assertEquals(startId,authorCollaboration.getStartId());
        assertEquals(endId,authorCollaboration.getEndId());
        assertEquals(endName,authorCollaboration.getEndName());
        assertEquals(distance,authorCollaboration.getDistance());
        assertEquals(directions,authorCollaboration.getDirections());

    }

    @Test
    void findAuthorCollaborationByAuthorId1(){
        long startId = 0;
        List<AuthorCollaboration> authorCollaborations =
                authorCollaborationDAO.findAuthorCollaborationByAuthorId(startId,0,1,15);
        assertNotNull(authorCollaborations);
        assertEquals(0,authorCollaborations.size());
    }

    @Test
    void findAuthorCollaborationByAuthorId2(){
        long startId = 1;
        List<AuthorCollaboration> authorCollaborations =
                authorCollaborationDAO.findAuthorCollaborationByAuthorId(startId,0,1,15);
        assertNotNull(authorCollaborations);
        assertEquals(4,authorCollaborations.size());
    }

    @Test
    void findAuthorCollaborationByAuthorId3(){
        long startId = 1;
        List<AuthorCollaboration> authorCollaborations =
                authorCollaborationDAO.findAuthorCollaborationByAuthorId(startId,0,1,1);
        assertNotNull(authorCollaborations);
        assertEquals(1,authorCollaborations.size());
    }
}