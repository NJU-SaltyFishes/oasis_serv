package nju.oasis.serv.dao;

import nju.oasis.serv.OasisServApplicationTests;
import nju.oasis.serv.domain.Article;
import nju.oasis.serv.domain.CoAuthor;
import nju.oasis.serv.domain.YDirection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorDAOTest extends OasisServApplicationTests {

    @Resource
    AuthorDAO authorDAO;

    @Test
    void findMaxCitationByIds() {
        List<Long>articleIds = new ArrayList<>();
        articleIds.add(Long.valueOf("1000189586"));
        articleIds.add(Long.valueOf("1000189582"));

        Article article = authorDAO.findMaxCitationByIds(articleIds);
        assertEquals(Long.valueOf("1000189586"),article.getId());
        assertEquals("Predictive performance modeling of virtualized storage" +
                " systems using optimized statistical regression techniques",article.getTitle());

    }

    @Test
    void findMostFrequentCoAuthor(){
        List<Long>articleIds = new ArrayList<>();
        articleIds.add(Long.valueOf("1000189586"));
        articleIds.add(Long.valueOf("1000191432"));
        articleIds.add(Long.valueOf("1000273554"));

        long authorId = Long.valueOf("108054");
        CoAuthor coAuthor = authorDAO.findMostFrequentCoAuthor(authorId,articleIds);
        assertEquals(Long.valueOf("66489"),coAuthor.getAuthorId());
        assertEquals(3,coAuthor.getCooperationTimes());
    }

    @Test
    void findDirectionYear(){
        long authorId = 1;
        List<YDirection>yDirections = authorDAO.findDirectionYear(authorId);
        assertEquals(1,yDirections.size());
        assertEquals(2013,yDirections.get(0).getYear());
    }

    @Test
    void findDirectionYear1(){
        long authorId = 2;
        List<YDirection>yDirections = authorDAO.findDirectionYear(authorId);
        assertEquals(2,yDirections.size());
        assertEquals(2012,yDirections.get(0).getYear());
        assertEquals(2016,yDirections.get(1).getYear());
    }

    @Test
    void  findDirectionYear2(){
        long authorId = 1000000000;
        List<YDirection>yDirections = authorDAO.findDirectionYear(authorId);
        assertEquals(0,yDirections.size());
    }

    @Test
    void getPredictDirection(){
        long authorId = 1;
        assertEquals("dynamic time warping",authorDAO.getPredictDirection(authorId));

    }

    @Test
    void getPredictDirection1(){
        long authorId = 89;
        assertNull(authorDAO.getPredictDirection(authorId));
    }

    @Test
    void getPredictDirection2(){
        long authorId = 1000000000;
        assertNull(authorDAO.getPredictDirection(authorId));
    }
}