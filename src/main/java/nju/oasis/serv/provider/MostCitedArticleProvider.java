package nju.oasis.serv.provider;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AuthorDAO;
import nju.oasis.serv.domain.Article;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
                articleIds.forEach(item -> log.warn("[MostCitedArticleProvider]: articleIds" + articleIds.toString() +
                        " contain invalid id: " + item));
                return false;

            }
            contextDataMap.put("mostCitedArticle", article);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        try{
            if(articleIds==null){
                return false;
            }
        }catch (Exception ex){
            log.warn("[MostCitedArticleProvider] error: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public String toString(){
        return "[MostCitedArticleProvider]";
    }
}
