package nju.oasis.serv.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.DirectionDAO;
import nju.oasis.serv.dao.PublicationDAO;
import nju.oasis.serv.service.RecommendService;
import nju.oasis.serv.vo.ReaderRecommendForm;
import nju.oasis.serv.vo.ResponseVO;
import nju.oasis.serv.vo.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    DirectionDAO directionDAO;

    @Resource
    PublicationDAO publicationDAO;

    @Override
    public ResponseVO recommendReader(ReaderRecommendForm readerRecommendForm){
        if(readerRecommendForm==null||readerRecommendForm.getTitle()==null){
            log.error("[recommendReader] recommendReader and its title must exist but was null!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }

        return null;
    }

    @Override
    public ResponseVO recommendDirection(String prefix){
        if(prefix==null||prefix.length()<1){
            log.error("[recommendDirection] recommendDirection must exist but was null or length < 1!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        Map<String,Object>result = new HashMap<>();
        result.put("directions",directionDAO.getDirectionsByPrefix(prefix));
        return ResponseVO.output(ResultCode.SUCCESS,result);
    }

    @Override
    public ResponseVO recommendPublication(String prefix){
        if(prefix==null||prefix.length()<1){
            log.error("[recommendPublication] recommendPublication must exist but was null or length < 1!");
            return ResponseVO.output(ResultCode.PARAM_ERROR,null);
        }
        Map<String,Object>result = new HashMap<>();
        result.put("publications",publicationDAO.getPublicationsByPrefix(prefix));
        return ResponseVO.output(ResultCode.SUCCESS,result);
    }
}
