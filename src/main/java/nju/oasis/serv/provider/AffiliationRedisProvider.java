package nju.oasis.serv.provider;

import com.alibaba.fastjson.JSON;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.config.Model;
import nju.oasis.serv.domain.AffiliationPublication;
import nju.oasis.serv.domain.CollaborationAffiliation;
import nju.oasis.serv.domain.Direction;
import nju.oasis.serv.domain.CitedAuthor;
import nju.oasis.serv.vo.PublicationYear;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
            List<Direction> directions = new ArrayList<>();
            CitedAuthor topCitedAuthor;
            List<CollaborationAffiliation> collaborationAffiliations = new ArrayList<>();
            CollaborationAffiliation topCoAffiliation = null;
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
                directions = JSON.parseArray(valueOperations.get(keywordsKey), Direction.class);
            }
            else{
                log.debug("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have directions!");
            }
            contextDataMap.put("directions", directions);

            //获取机构引用最多作者
            if(redisTemplate.hasKey(mostCitedAuthorsKey)){
                topCitedAuthor = JSON.parseObject(valueOperations.get(mostCitedAuthorsKey), CitedAuthor.class);
                contextDataMap.put("topCitedAuthor", topCitedAuthor);
            }
            else{
                log.debug("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have topCitedAuthors!");
                contextDataMap.put("topCitedAuthors",null);
            }

            //获取机构的合作机构
            if(redisTemplate.hasKey(collaborationPublicationKey)){
                collaborationAffiliations =
                        JSON.parseArray(valueOperations.get(collaborationPublicationKey), CollaborationAffiliation.class);
                topCoAffiliation = collaborationAffiliations.get(0);
            }
            else{
                log.debug("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have collaborationAffiliation!");
            }
            contextDataMap.put("collaborationAffiliation", collaborationAffiliations);
            contextDataMap.put("topCoAffiliation",topCoAffiliation);

            if(redisTemplate.hasKey(affiliationYearKey)){
                affiliationPublication =
                        JSON.parseObject(valueOperations.get(affiliationYearKey),AffiliationPublication.class);
                List<PublicationYear>publicationYear = new ArrayList<>();
                publicationYear.add(new PublicationYear(2010,affiliationPublication.getCount2010()));
                publicationYear.add(new PublicationYear(2011,affiliationPublication.getCount2011()));
                publicationYear.add(new PublicationYear(2012,affiliationPublication.getCount2012()));
                publicationYear.add(new PublicationYear(2013,affiliationPublication.getCount2013()));
                publicationYear.add(new PublicationYear(2014,affiliationPublication.getCount2014()));
                publicationYear.add(new PublicationYear(2015,affiliationPublication.getCount2015()));
                publicationYear.add(new PublicationYear(2016,affiliationPublication.getCount2016()));
                publicationYear.add(new PublicationYear(2017,affiliationPublication.getCount2017()));
                publicationYear.add(new PublicationYear(2018,affiliationPublication.getCount2018()));
                publicationYear.add(new PublicationYear(2019,affiliationPublication.getCount2019()));
                publicationYear.add(new PublicationYear(2020,affiliationPublication.getCount2020()));
                contextDataMap.put("publicationYear",publicationYear);
            }
            else{
                log.debug("[affiliationRedisProvider]: affiliationId:"+affiliationId+" doesn't have affiliationYearKey!");
                contextDataMap.put("publicationYear",new ArrayList<PublicationYear>());
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
