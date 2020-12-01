package nju.oasis.serv.provider;

import com.alibaba.fastjson.JSON;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.config.Model;
import nju.oasis.serv.domain.AffiliationPublication;
import nju.oasis.serv.domain.CollaborationPublication;
import nju.oasis.serv.domain.Keyword;
import nju.oasis.serv.domain.MostCitedAuthor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import nju.oasis.serv.config.Model.*;
import org.springframework.data.redis.core.ValueOperations;
// file deepcode ignore LogLevelCheck~debug: already checked the log level
// file deepcode ignore GuardLogStatement: It has little effect on performance
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
            String newestArticleIdKey =
                    Model.AFFILIATION_RELATED_NEW_ARTICLE_ID_KEY_TEMPLATE+affiliationId;
            String keywordsKey = Model.AFFILIATION_RELATED_KEYWORD_KEY_TEMPLATE+affiliationId;
            String mostCitedAuthorsKey =
                    Model.AFFILIATION_RELATED_MOST_CITED_AUTHOR_KEY_TEMPLATE+affiliationId;
            String collaborationPublicationKey =
                    Model.AFFILIATION_COLLABORATION_PUBLICATION_COUNT+affiliationId;
            String affiliationYearKey = Model.AFFILIATION_YEAR_COUNT+affiliationId;
            ValueOperations<String,String>valueOperations = redisTemplate.opsForValue();
            long newestArticleId = -1;
            List<Keyword>keywords = new ArrayList<>();
            MostCitedAuthor mostCitedAuthor;
            List<CollaborationPublication> collaborationPublications = new ArrayList<>();
            AffiliationPublication affiliationPublication;
            //获取机构发表最新文章
            if(redisTemplate.hasKey(newestArticleIdKey)){
                newestArticleId =
                        Long.parseLong(valueOperations.get(newestArticleIdKey));
            }
            else{
                log.debug("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have newestArticleId!");
            }
            contextDataMap.put("newestArticleId",newestArticleId);

            //获取机构关键字
            if(redisTemplate.hasKey(keywordsKey)){
                keywords = JSON.parseArray(valueOperations.get(keywordsKey),Keyword.class);
            }
            else{
                log.debug("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have keywords!");
            }
            contextDataMap.put("keywords",keywords);

            //获取机构引用最多作者
            if(redisTemplate.hasKey(mostCitedAuthorsKey)){
                mostCitedAuthor = JSON.parseObject(valueOperations.get(mostCitedAuthorsKey),MostCitedAuthor.class);
                contextDataMap.put("mostCitedAuthors",mostCitedAuthor);
            }
            else{
                log.debug("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have mostCitedAuthors!");
                contextDataMap.put("mostCitedAuthors",null);
            }

            //获取机构的合作机构
            if(redisTemplate.hasKey(collaborationPublicationKey)){
                collaborationPublications =
                        JSON.parseArray(valueOperations.get(collaborationPublicationKey),CollaborationPublication.class);
            }
            else{
                log.debug("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have collaborationPublication!");
            }
            contextDataMap.put("collaborationPublication",collaborationPublications);

            if(redisTemplate.hasKey(affiliationYearKey)){
                affiliationPublication =
                        JSON.parseObject(valueOperations.get(affiliationYearKey),AffiliationPublication.class);
                contextDataMap.put("affiliationYear",affiliationPublication);
            }
            else{
                log.debug("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have affiliationYearKey!");
                contextDataMap.put("affiliationYear",new AffiliationPublication());
            }
            return true;
        }catch (Exception ex){
            log.error("[affiliationRedisProvider] error: " + ex.getMessage());
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
