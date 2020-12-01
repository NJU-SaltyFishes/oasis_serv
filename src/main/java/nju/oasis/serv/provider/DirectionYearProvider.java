package nju.oasis.serv.provider;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AuthorDAO;
import nju.oasis.serv.domain.Keyword;
import nju.oasis.serv.domain.YDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor
public class DirectionYearProvider extends Provider{

    private AuthorDAO authorDAO;

    private long authorId;

    public DirectionYearProvider(AuthorDAO authorDAO,long authorId){
        this.authorDAO = authorDAO;
        this.authorId = authorId;
    }

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        try {
            List<YDirection>yDirections = authorDAO.findDirectionYear(authorId);
            if(yDirections==null||yDirections.size()==0){
                log.debug("[DirectionYearProvider]: authorId:"+authorId+" may not have direction");
            }
            yDirections.forEach(yDirection->{
                String directions = yDirection.getDirections();
                List<Keyword>formatDirections = new ArrayList<>();
                List<String>directionList = Arrays.asList(directions.split(","));
                directionList.forEach(direction->{
                    String[]directionIdAndName = direction.split(":");
                    Keyword keyword = new Keyword();
                    keyword.setKeywordId(Long.valueOf(directionIdAndName[0]));
                    keyword.setKeywordDesc(directionIdAndName[1]);
                    formatDirections.add(keyword);
                });
                yDirection.setFormatDirections(formatDirections);
            });
            contextDataMap.put("directions",yDirections);
            return true;
        }catch (Exception ex){
            log.error("[DirectionYearProvider] error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        if(authorId<=0){
            log.warn("[DirectionYearProvider]:authorId<=0!");
            return false;
        }
        return true;
    }

    public String toString(){
        return "[DirectionYearProvider]";
    }
}
