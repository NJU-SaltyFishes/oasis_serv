package nju.oasis.serv.provider;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AuthorDAO;
import nju.oasis.serv.domain.Direction;
import nju.oasis.serv.domain.YDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor
public class PredictDirectionProvider extends Provider{

    private AuthorDAO authorDAO;

    private long authorId;

    public PredictDirectionProvider(AuthorDAO authorDAO,long authorId){
        this.authorDAO = authorDAO;
        this.authorId = authorId;
    }

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        try {
            String predictDirection = authorDAO.getPredictDirection(authorId);
            if(predictDirection==null){
                log.debug("[PredictDirectionProvider]: authorId:"+authorId+" may not have predict direction");
            }
            contextDataMap.put("predictDirection",predictDirection);
            return true;
        }catch (Exception ex){
            log.error("[PredictDirectionProvider] error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        if(authorId<=0){
            log.warn("[PredictDirectionProvider]:authorId<=0!");
            return false;
        }
        return true;
    }

    public String toString(){
        return "[PredictDirectionProvider]";
    }
}
