package com.ll.exam.app3;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteUserController {

    @RequestMapping("/user/{id}")
    public void create(@PathVariable Long id){

    }

}
