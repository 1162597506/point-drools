package com.liuzhe.drools.controller;

import com.liuzhe.drools.dao.RuleDao;
import com.liuzhe.drools.entity.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Alan on 2018/8/6.
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    RuleDao ruleDao;

    @RequestMapping("/addRule")
    public void addRule(@RequestParam String name, @RequestParam String rule) {
        ruleDao.setRule(name,rule);
    }
}
