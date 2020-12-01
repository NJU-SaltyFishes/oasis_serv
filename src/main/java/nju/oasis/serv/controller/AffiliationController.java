package nju.oasis.serv.controller;

import nju.oasis.serv.service.AffiliationService;
import nju.oasis.serv.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@ResponseBody
@RequestMapping("/affiliation")
public class AffiliationController {

    @Autowired
    AffiliationService affiliationService;

    /**
     * 通过id查找机构相关信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseVO findAffiliationInfoById(@PathVariable long id){
        return affiliationService.findAffiliationInfoById(id);
    }
}
