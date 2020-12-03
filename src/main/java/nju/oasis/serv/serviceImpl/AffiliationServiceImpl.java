package nju.oasis.serv.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AffiliationDAO;
import nju.oasis.serv.domain.CollaborationAffiliation;
import nju.oasis.serv.domain.Direction;
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
// file deepcode ignore GuardLogStatement: It has little effect on performance
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
            log.warn("[findAffiliationInfoById]: affiliationId<=0!");
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
                List<Direction> directions = (List<Direction>)value;
                //第一关键字
                if(directions.size()>0)result.put("topKeyword", directions.get(0));
            }
            else if(key.equals("collaborationPublication")){
                List<CollaborationAffiliation> collaborationAffiliations =
                        (List<CollaborationAffiliation>)value;
                //合作最多的机构
                if(collaborationAffiliations.size()>0)result.put("topCollaboration",
                        collaborationAffiliations.get(0));
            }
        });
        return ResponseVO.output(ResultCode.SUCCESS,result);
    }
}
