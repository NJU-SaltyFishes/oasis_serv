package nju.oasis.serv.service;

import nju.oasis.serv.vo.AuthorRequestForm;
import nju.oasis.serv.vo.ResponseVO;

import java.util.List;

public interface AuthorService {

    ResponseVO findById(AuthorRequestForm authorRequestForm);
}
