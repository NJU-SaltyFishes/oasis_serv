package nju.oasis.serv.service;

import nju.oasis.serv.vo.AuthorRequestForm;
import nju.oasis.serv.vo.ResponseVO;


public interface AuthorService {

    ResponseVO findById(AuthorRequestForm authorRequestForm);

    ResponseVO findRelationsById(long id,int minLevel,int maxLevel,int numOfEachLayer);
}
