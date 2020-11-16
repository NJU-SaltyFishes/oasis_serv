package nju.oasis.serv.provider;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AuthorDAO;
import nju.oasis.serv.domain.Article;
import nju.oasis.serv.domain.CoAuthor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CoAuthorProvider extends Provider{

    @Resource
    private AuthorDAO authorDAO;

    private long authorId;

    private List<Long> articleIds;

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        try {
            CoAuthor coAuthor = authorDAO.findMostFrequentCoAuthor(authorId, articleIds);
            if (coAuthor == null) {
                log.warn("[CoAuthorProvider]: authorId:" + authorId + "may not have coAuthor");
                articleIds.forEach(item -> log.warn("[CoAuthorProvider]: articleIds" + articleIds.toString() +
                        "may contain invalid id: " + item));
                return false;
            }
            contextDataMap.put("coAuthor", coAuthor);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        try{
            if(contextDataMap.get("articleIds")==null||contextDataMap.get("authorId")==null){
                return false;
            }
            this.articleIds = (List<Long>)contextDataMap.get("articleIds");
            this.authorId = (long)contextDataMap.get("authorId");
        }catch (Exception ex){
            log.warn("[CoAuthorProvider] error: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public String toString(){
        return "[CoAuthorProvider]";
    }
}
