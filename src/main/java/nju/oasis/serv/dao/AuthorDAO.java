package nju.oasis.serv.dao;

import nju.oasis.serv.domain.Article;
import nju.oasis.serv.domain.CoAuthor;
import nju.oasis.serv.domain.YDirection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorDAO {

    /**
     * 寻找作者被引用最多的论文
     * @param articleIds
     * @return
     */
    Article findMaxCitationByIds(List<Long>articleIds);

    /**
     * 寻找和author合作最频繁的作者的ID
     * @param authorId 当前author的ID
     * @param articleIds
     * @return
     */
    CoAuthor findMostFrequentCoAuthor(@Param("authorId") Long authorId, @Param("articleIds") List<Long>articleIds);

    /**
     * 识别研究者在不同阶段的研究兴趣
     * @param authorId
     * @return
     */
    List<YDirection>findDirectionYear(Long authorId);

    String getPredictDirection(Long authorId);
}
