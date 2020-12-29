package nju.oasis.serv.controller;

import nju.oasis.serv.service.RecommendService;
import nju.oasis.serv.vo.ReaderRecommendForm;
import nju.oasis.serv.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@ResponseBody
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    RecommendService recommendService;

    @RequestMapping(value = "/reader",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseVO recommendReader(@RequestBody ReaderRecommendForm readerRecommendForm){
        return recommendService.recommendReader(readerRecommendForm);
    }

    @RequestMapping(value = "/direction",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseVO recommendDirection(@RequestParam("prefix")String prefix){
        return recommendService.recommendDirection(prefix);
    }

    @RequestMapping(value = "/publication",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public ResponseVO recommendPublication(@RequestParam("prefix")String prefix){
        return recommendService.recommendPublication(prefix);
    }
}
