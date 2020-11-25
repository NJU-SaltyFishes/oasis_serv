package nju.oasis.serv.provider;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nju.oasis.serv.dao.AffiliationDAO;
import nju.oasis.serv.domain.AffiliationInfo;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor
public class AffiliationDatabaseProvider extends Provider {

    private AffiliationDAO affiliationDAO;

    private long affiliationId;

    public AffiliationDatabaseProvider(AffiliationDAO affiliationDAO,long affiliationId){
        this.affiliationDAO = affiliationDAO;
        this.affiliationId = affiliationId;
    }

    @Override
    public boolean provide(ConcurrentHashMap<String, Object> contextDataMap) {
        try{
            AffiliationInfo affiliationInfo = affiliationDAO.findAffiliationInfoById(affiliationId);
            if(affiliationInfo==null){
                log.warn("[affiliationDatabaseProvider]: affiliationId:"+affiliationId+" doesn't have " +
                        "affiliation information!" );
                return false;
            }
            contextDataMap.put("affiliationInfo",affiliationInfo);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("[affiliationDatabaseProvider] error: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean parseParams(ConcurrentHashMap<String, Object> contextDataMap) {
        if(affiliationId<=0){
            log.debug("[affiliationDatabaseProvider]: affiliationId<=0!");
            return false;
        }
        return true;
    }

    public String toString(){
        return "[affiliationDatabaseProvider]";
    }
}
