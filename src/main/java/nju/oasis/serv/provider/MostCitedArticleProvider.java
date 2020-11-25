package nju.oasis.serv.provider;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AuthorDAO;
import nju.oasis.serv.domain.Article;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
// file deepcode ignore LogLevelCheck: It has little effect on performance
// file deepcode ignore GuardLogStatement: It has little effect on performance
@Slf4j
@NoArgsConstructor
public class MostCitedArticleProvider extends Provider {

    private AuthorDAO authorDAO;

    private List<Long> articleIds;

    public MostCitedArticleProvider(AuthorDAO authorDAO,List<Long>articleIds){
        this.authorDAO = authorDAO;
        this.articleIds = articleIds;
    }

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        try {
            Article article = authorDAO.findMaxCitationByIds(articleIds);
            if (article == null) {
                articleIds.forEach(item -> log.debug("[MostCitedArticleProvider]: articleIds" + articleIds.toString() +
                        " contain invalid id: " + item));
            }
            contextDataMap.put("mostCitedArticle", article);
            return true;
        }catch (Exception ex){
            log.error("[MostCitedArticleProvider] error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        if(articleIds==null){
            log.warn("[MostCitedArticleProvider]:articleIds is null!");
            return false;
        }
        return true;
    }

    public String toString(){
        return "[MostCitedArticleProvider]";
    }
}
