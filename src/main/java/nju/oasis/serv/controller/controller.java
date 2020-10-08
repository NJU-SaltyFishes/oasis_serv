package nju.oasis.serv.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class controller {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestParam String name){
        return "Hello! " + name;
    }
}
