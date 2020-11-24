package nju.oasis.serv.provider;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.config.Model;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import nju.oasis.serv.config.Model.*;

@Slf4j
@NoArgsConstructor
public class AffiliationRedisProvider extends Provider {

    private RedisTemplate redisTemplate;

    private long affiliationId;

    public AffiliationRedisProvider(RedisTemplate redisTemplate,long affiliationId){
        this.redisTemplate = redisTemplate;
        this.affiliationId = affiliationId;
    }

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        try{
            //机构发表最新文章ID
            long newestArticleId = -1;
            //机构关键字
            Map<String,Object>keywords;
            //机构被引用最多作者
            List<Object>mostCitedAuthors;
            //机构的合作机构
            List<Map<String,Integer>>collaborationPublication;
            //机构每年发表的文章
            Map<Integer,Integer>affiliationYear;

            String newestArticleIdKey =
                    Model.AFFILIATION_RELATED_NEW_ARTICLE_ID_KEY_TEMPLATE+affiliationId;
            String keywordsKey = Model.AFFILIATION_RELATED_KEYWORD_KEY_TEMPLATE+affiliationId;
            String mostCitedAuthorsKey =
                    Model.AFFILIATION_RELATED_MOST_CITED_AUTHOR_KEY_TEMPLATE+affiliationId;
            String collaborationPublicationKey =
                    Model.AFFILIATION_COLLABORATION_PUBLICATION_COUNT+affiliationId;
            String affiliationYearKey = Model.AFFILIATION_YEAR_COUNT+affiliationId;
            Set<String> keys = redisTemplate.keys("*");
            if(redisTemplate.hasKey(newestArticleIdKey)){
                newestArticleId = (long)redisTemplate.opsForValue().get(newestArticleIdKey);
                contextDataMap.put("newestArticleId",newestArticleId);
            }
            else{
                log.warn("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have newestArticleId!");
            }

            if(redisTemplate.hasKey(keywordsKey)){
                keywords = redisTemplate.opsForHash().entries(keywordsKey);
                contextDataMap.put("keywords",keywords);
            }
            else{
                log.warn("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have keywords!");
            }

            if(redisTemplate.hasKey(mostCitedAuthorsKey)){
                mostCitedAuthors = redisTemplate.opsForList().range(mostCitedAuthorsKey,0,-1);
                contextDataMap.put("mostCitedAuthors",mostCitedAuthors);
            }
            else{
                log.warn("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have mostCitedAuthors!");
            }

            if(redisTemplate.hasKey(collaborationPublicationKey)){
                collaborationPublication = redisTemplate.opsForList().
                        range(collaborationPublicationKey,0,-1);
                contextDataMap.put("collaborationPublication",collaborationPublication);
            }
            else{
                log.warn("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have collaborationPublication!");
            }

            if(redisTemplate.hasKey(affiliationYearKey)){
                affiliationYear = redisTemplate.opsForHash().entries(affiliationYearKey);
                contextDataMap.put("affiliationYear",affiliationYear);
            }
            else{
                log.warn("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have affiliationYearKey!");
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            log.warn("[affiliationDatabaseProvider] error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        if(affiliationId<=0){
            log.warn("[affiliationRedisProvider]: affiliationId<=0!");
            return false;
        }
        return true;
    }

    public String toString(){
        return "[affiliationRedisProvider]";
    }
}
