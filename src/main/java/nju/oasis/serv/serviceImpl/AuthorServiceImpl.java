package nju.oasis.serv.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AuthorDAO;
import nju.oasis.serv.dao.AuthorNeo4jDAO;
import nju.oasis.serv.domain.Article;
import nju.oasis.serv.domain.CoAuthor;
import nju.oasis.serv.domain.DAuthor;
import nju.oasis.serv.domain.YDirection;
import nju.oasis.serv.provider.CoAuthorProvider;
import nju.oasis.serv.provider.DirectionYearProvider;
import nju.oasis.serv.provider.MostCitedArticleProvider;
import nju.oasis.serv.provider.Pipeline;
import nju.oasis.serv.service.AuthorService;
import nju.oasis.serv.vo.AuthorRequestForm;
import nju.oasis.serv.vo.ResponseVO;
import nju.oasis.serv.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
// file deepcode ignore GuardLogStatement: It has little effect on performance
@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    @Resource
    AuthorDAO authorDAO;

    @Autowired
    AuthorNeo4jDAO authorNeo4jDAO;

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

        MostCitedArticleProvider mostCitedArticleProvider = new
                MostCitedArticleProvider(authorDAO,authorRequestForm.getArticleIds());
        CoAuthorProvider coAuthorProvider = new CoAuthorProvider(authorDAO,
                authorRequestForm.getAuthorId(),authorRequestForm.getArticleIds());
        DirectionYearProvider directionYearProvider = new DirectionYearProvider(
                authorDAO,authorRequestForm.getAuthorId()
        );
        pipeline.addProviderAsGroup(mostCitedArticleProvider,coAuthorProvider,directionYearProvider);
        pipeline.doPipeline();
        ConcurrentHashMap<String, Object> concurrentHashMap = pipeline.getContextData();
        if(!concurrentHashMap.containsKey("mostCitedArticle")){
            log.warn("[findById] author:"+authorRequestForm.getAuthorId()+" doesn't have articles!");
        }
        else{
            Article article = (Article) concurrentHashMap.get("mostCitedArticle");
            result.put("articleId",article.getId());
            result.put("articleName",article.getTitle());
        }
        if(!concurrentHashMap.containsKey("coAuthor")){
            log.warn("[findById] author:"+authorRequestForm.getAuthorId()+" doesn't have coAuthor!");
        }
        else {
            CoAuthor coAuthor = (CoAuthor) concurrentHashMap.get("coAuthor");
            result.put("coAuthorId",coAuthor.getAuthorId());
            result.put("coAuthorCooperationTimes",coAuthor.getCooperationTimes());
        }
        if(!concurrentHashMap.containsKey("directions")){
            log.warn("[findById] author:"+authorRequestForm.getAuthorId()+" doesn't have directions!");
        }
        else{
            List<YDirection>yDirections = (List)concurrentHashMap.get("directions");
            result.put("directionYear",yDirections);
        }
        return ResponseVO.output(ResultCode.SUCCESS,result);
    }

    @Override
    public ResponseVO findRelationsById(long id,int minLevel,int maxLevel,int numOfEachLayer){
        if(id<=0){
            log.warn("[findRelationsById] authorId must be greater than 0!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        List<DAuthor>dAuthors = authorNeo4jDAO.findByAuthorId(id,minLevel,maxLevel,numOfEachLayer);
        if(dAuthors.size()<maxLevel){
            log.warn("[findRelationsById] authorId:" + id + " may not have "+maxLevel+" layers!");
            for(int i=dAuthors.size()+1;i<=maxLevel;i++){
                dAuthors.add(new DAuthor(i));
            }
        }
        return ResponseVO.output(ResultCode.SUCCESS,dAuthors);
    }
}
