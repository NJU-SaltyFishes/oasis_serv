package nju.oasis.serv.service;

import nju.oasis.serv.OasisServApplicationTests;
import nju.oasis.serv.domain.Article;
import nju.oasis.serv.vo.AuthorRequestForm;
import nju.oasis.serv.vo.ResponseVO;
import nju.oasis.serv.vo.ResultCode;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest extends OasisServApplicationTests {

    @Autowired
    AuthorService authorService;

    @Test
    void findById() {
        AuthorRequestForm authorRequestForm = new AuthorRequestForm();
        List<Long>articleIds = new ArrayList<>();
        articleIds.add(Long.valueOf(1000196154));

        authorRequestForm.setAuthorId(Long.valueOf(1));
        authorRequestForm.setArticleIds(articleIds);

        ResponseVO responseVO = authorService.findById(authorRequestForm);
        assertEquals(0,responseVO.getStatus());
        Map<String, Object> result = (Map<String, Object>) responseVO.getData();
        assertTrue(result.containsKey("mostCitedArticle"));
        assertTrue(result.containsKey("coAuthor"));
    }
}