package nju.oasis.serv.dao;

import nju.oasis.serv.domain.AffiliationInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AffiliationDAO {

    AffiliationInfo findAffiliationInfoById(@Param("affiliationId") long affiliationId);
}
