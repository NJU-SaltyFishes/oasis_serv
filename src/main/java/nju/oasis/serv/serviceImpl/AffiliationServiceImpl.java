package nju.oasis.serv.serviceImpl;

import nju.oasis.serv.dao.AffiliationDAO;
import nju.oasis.serv.provider.AffiliationDatabaseProvider;
import nju.oasis.serv.provider.AffiliationRedisProvider;
import nju.oasis.serv.provider.Pipeline;
import nju.oasis.serv.service.AffiliationService;
import nju.oasis.serv.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AffiliationServiceImpl implements AffiliationService {

    @Resource
    AffiliationDAO affiliationDAO;
    @Autowired
    RedisTemplate redisTemplate;


    public ResponseVO findAffiliationInfoById(long id){

        Map<String, Object> result = new HashMap<>();
        //新建pipeline，并发跑查找引用最多论文和查找合作最多作者任务
        Pipeline pipeline = new Pipeline();
        AffiliationDatabaseProvider affiliationDatabaseProvider
                = new AffiliationDatabaseProvider(affiliationDAO,id);
        AffiliationRedisProvider affiliationRedisProvider
                = new AffiliationRedisProvider(redisTemplate,id);
        pipeline.addProviderAsGroup(affiliationDatabaseProvider,affiliationRedisProvider);
        pipeline.doPipeline();
        ConcurrentHashMap<String, Object> concurrentHashMap = pipeline.getContextData();
        return null;
    }
}
