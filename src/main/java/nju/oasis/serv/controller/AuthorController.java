package nju.oasis.serv.controller;

import nju.oasis.serv.service.AuthorService;
import nju.oasis.serv.vo.AuthorRequestForm;
import nju.oasis.serv.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@ResponseBody
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    /**
     * 获取作者对应文章中被引用最多的文章，以及合作次数最多的作者
     * @param authorRequestForm
     * @return
     */
    @RequestMapping(value = "/articles",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResponseVO findById(@RequestBody AuthorRequestForm authorRequestForm){
        return authorService.findById(authorRequestForm);
    }
}
