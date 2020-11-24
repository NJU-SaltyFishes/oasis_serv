package nju.oasis.serv.service;

import nju.oasis.serv.vo.ResponseVO;

public interface AffiliationService {
    ResponseVO findAffiliationInfoById(long id);
}
