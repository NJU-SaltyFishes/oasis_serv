package nju.oasis.serv.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AffiliationDAO;
import nju.oasis.serv.domain.AffiliationInfo;
import nju.oasis.serv.domain.CollaborationPublication;
import nju.oasis.serv.domain.Keyword;
import nju.oasis.serv.provider.AffiliationDatabaseProvider;
import nju.oasis.serv.provider.AffiliationRedisProvider;
import nju.oasis.serv.provider.Pipeline;
import nju.oasis.serv.service.AffiliationService;
import nju.oasis.serv.vo.ResponseVO;
import nju.oasis.serv.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class AffiliationServiceImpl implements AffiliationService {

    @Resource
    AffiliationDAO affiliationDAO;
    @Autowired
    RedisTemplate redisTemplate;


    public ResponseVO findAffiliationInfoById(long id){

        //新建pipeline，并发跑查找引用最多论文和查找合作最多作者任务
        if(id<=0){
            log.debug("[findAffiliationInfoById]: affiliationId<=0!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        Map<String, Object> result = new HashMap<>();
        Pipeline pipeline = new Pipeline();
        AffiliationDatabaseProvider affiliationDatabaseProvider
                = new AffiliationDatabaseProvider(affiliationDAO,id);
        AffiliationRedisProvider affiliationRedisProvider
                = new AffiliationRedisProvider(redisTemplate,id);
        pipeline.addProviderAsGroup(affiliationDatabaseProvider,affiliationRedisProvider);
        pipeline.doPipeline();
        ConcurrentHashMap<String, Object> concurrentHashMap = pipeline.getContextData();
        concurrentHashMap.forEach((key,value)->{
            result.put(key,value);
            if(key.equals("keywords")){
                List<Keyword>keywords = (List<Keyword>)value;
                //第一关键字
                if(keywords.size()>0)result.put("topKeyword",keywords.get(0));
            }
            else if(key.equals("collaborationPublication")){
                List<CollaborationPublication> collaborationPublications =
                        (List<CollaborationPublication>)value;
                //合作最多的机构
                if(collaborationPublications.size()>0)result.put("topCollaboration",
                        collaborationPublications.get(0));
            }
        });
        return ResponseVO.output(ResultCode.SUCCESS,result);
    }
}
