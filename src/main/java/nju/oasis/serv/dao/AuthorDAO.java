package nju.oasis.serv.dao;

import nju.oasis.serv.domain.Article;
import nju.oasis.serv.domain.CoAuthor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorDAO {

    Article findMaxCitationByIds(List<Long>articleIds);

    /**
     * 寻找和author合作最频繁的作者的ID
     * @param authorId 当前author的ID
     * @param articleIds
     * @return
     */
    CoAuthor findMostFrequentCoAuthor(@Param("authorId") Long authorId, @Param("articleIds") List<Long>articleIds);
}
