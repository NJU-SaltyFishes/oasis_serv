package nju.oasis.serv.dao;

import nju.oasis.serv.OasisServApplicationTests;
import nju.oasis.serv.domain.AffiliationInfo;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class AffiliationDAOTest extends OasisServApplicationTests {

    @Resource
    AffiliationDAO affiliationDAO;

    @Test
    void findAffiliationInfoById() {
        AffiliationInfo affiliationInfo =
                affiliationDAO.findAffiliationInfoById(1000000003);
        assertNotNull(affiliationInfo);
        assertEquals(1000000003,affiliationInfo.getAffiliationId());
        assertEquals("Tianjin University of Science and Technology",affiliationInfo.getAffiliationName());
        assertEquals(1.14,affiliationInfo.getAverageCitationPerArticle());
    }
}