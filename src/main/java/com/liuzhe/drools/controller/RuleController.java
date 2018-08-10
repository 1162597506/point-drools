package com.liuzhe.drools.controller;

import com.liuzhe.drools.dao.RuleDao;
import com.liuzhe.drools.entity.Rule;
import com.liuzhe.drools.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Alan on 2018/8/6.
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    RuleService ruleService;

    @RequestMapping("/addRule")
    public void addRule(@RequestParam String name, @RequestParam String rule) {
        ruleService.setRule(name,rule);
    }

    @RequestMapping("/deleteRule")
    public void deleteRule(@RequestParam int id ) {
        ruleService.deleteRule(id);
    }

    @RequestMapping("/updateRule")
    public void updateRule(@RequestParam int id, @RequestParam String name, @RequestParam String rule) {
        ruleService.updateRule(id, name, rule);
    }

    @RequestMapping("/getRule")
    public Rule getRule(@RequestParam int id) {
        return  ruleService.getRule(id);
    }

    @RequestMapping("/getRuleList")
    public List<Rule> getRuleList() {
        return ruleService.getRuleList();
    }
}
