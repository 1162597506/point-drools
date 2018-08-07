package com.liuzhe.drools;

import com.liuzhe.drools.controller.RuleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Alan on 2018/8/6.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RuleTest {

    @Autowired
    RuleController controller;

    @Test
    public void addRuleTest() {
        String name = "rule_name";
        String rule = "this is rule , hhhhhhh";
        controller.addRule(name, rule);
    }
}
