package nju.oasis.serv.dao;

import nju.oasis.serv.OasisServApplicationTests;
import nju.oasis.serv.domain.Article;
import nju.oasis.serv.domain.CoAuthor;
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
}