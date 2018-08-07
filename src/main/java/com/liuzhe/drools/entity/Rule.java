package com.liuzhe.drools.entity;
/**
 * Created by Alan on 2018/8/6.
 */
public class Rule {

    Integer id;
    String name;
    String rule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Rule(Integer id, String name, String rule) {
        this.id = id;
        this.name = name;
        this.rule = rule;
    }

    public Rule() {
    }
}
