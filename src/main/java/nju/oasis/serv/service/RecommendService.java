package nju.oasis.serv.service;

import nju.oasis.serv.vo.ReaderRecommendForm;
import nju.oasis.serv.vo.ResponseVO;

public interface RecommendService {

    ResponseVO recommendReader(ReaderRecommendForm readerRecommendForm);

    ResponseVO recommendDirection(String prefix);

    ResponseVO recommendPublication(String prefix);
}
