package nju.oasis.serv.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.domain.Article;
import nju.oasis.serv.domain.CoAuthor;
import nju.oasis.serv.provider.CoAuthorProvider;
import nju.oasis.serv.provider.MostCitedArticleProvider;
import nju.oasis.serv.provider.Pipeline;
import nju.oasis.serv.service.AuthorService;
import nju.oasis.serv.vo.AuthorRequestForm;
import nju.oasis.serv.vo.ResponseVO;
import nju.oasis.serv.vo.ResultCode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    @Override
    public ResponseVO findById(AuthorRequestForm authorRequestForm){
        if(authorRequestForm==null){
            log.warn("[findById] authorRequestForm must exist but was null!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        else if(authorRequestForm.getArticleIds()==null||authorRequestForm.getArticleIds().size()==0){
            log.warn("[findById] articles'size must >0 but articles  were null or articles'size=0");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        else if(authorRequestForm.getAuthorId()==null){
            log.warn("[findById] authorId must exist but was null!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        Map<String, Object> result = new HashMap<>();
        //新建pipeline，并发跑查找引用最多论文和查找合作最多作者任务
        Pipeline pipeline = new Pipeline();
        pipeline.setParamToContextData("authorId",authorRequestForm.getAuthorId());
        pipeline.setParamToContextData("articleIds",authorRequestForm.getArticleIds());
        pipeline.addProviderAsGroup(new MostCitedArticleProvider(),new CoAuthorProvider());
        pipeline.doPipeline();
        ConcurrentHashMap<String, Object> concurrentHashMap = pipeline.getContextData();

        for (String key:concurrentHashMap.keySet()){
            System.out.println(key +": " + concurrentHashMap.get(key));
        }

        if(!concurrentHashMap.contains("mostCitedArticle")){
            log.warn("[findById] author:"+authorRequestForm.getAuthorId()+" doesn't have articles!");
        }
        else{
            result.put("mostCitedArticle",result.get("mostCitedArticle"));
        }
        if(!concurrentHashMap.contains("coAuthor")){
            log.warn("[findById] author:"+authorRequestForm.getAuthorId()+" doesn't have coAuthor!");
        }
        else {
            result.put("coAuthor",result.get("coAuthor"));
        }
        return ResponseVO.output(ResultCode.SUCCESS,result);
    }
}
