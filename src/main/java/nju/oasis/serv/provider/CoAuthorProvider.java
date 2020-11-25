package nju.oasis.serv.provider;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AuthorDAO;
import nju.oasis.serv.domain.Article;
import nju.oasis.serv.domain.CoAuthor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor
public class CoAuthorProvider extends Provider{

    private AuthorDAO authorDAO;

    private long authorId;

    private List<Long> articleIds;

    public CoAuthorProvider(AuthorDAO authorDAO,long authorId,List<Long>articleIds){
        this.authorDAO = authorDAO;
        this.authorId = authorId;
        this.articleIds = articleIds;
    }

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        try {
            CoAuthor coAuthor = authorDAO.findMostFrequentCoAuthor(authorId, articleIds);
            if (coAuthor == null) {
                log.debug("[CoAuthorProvider]: authorId:" + authorId + "may not have coAuthor");
                articleIds.forEach(item -> log.warn("[CoAuthorProvider]: articleIds" + articleIds.toString() +
                        "may contain invalid id: " + item));
            }
            contextDataMap.put("coAuthor", coAuthor);
            return true;
        }catch (Exception ex){
            log.error("[CoAuthorProvider] error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {

        if(articleIds==null||authorId<=0){
            log.debug("[CoAuthorProvider]:articleIds is null or authorId<0!");
            return false;
        }
        return true;
    }

    public String toString(){
        return "[CoAuthorProvider]";
    }
}
