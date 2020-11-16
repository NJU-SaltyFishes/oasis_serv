package nju.oasis.serv.provider;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AuthorDAO;
import nju.oasis.serv.domain.Article;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MostCitedArticleProvider extends Provider {

    @Resource
    AuthorDAO authorDAO;

    private List<Long> articleIds;

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        Article article = authorDAO.findMaxCitationByIds(articleIds);
        if(article==null){
            articleIds.forEach(item->log.warn("[MostCitedArticleProvider]: articleIds"+ articleIds.toString() +
                    " contain invalid id: "+ item));
            return false;

        }
        contextDataMap.put("mostCitedArticle", article);
        return true;
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        try{
            if(contextDataMap.get("articleIds")==null){
                return false;
            }
            this.articleIds = (List<Long>)contextDataMap.get("articleIds");
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
