package com.liuzhe.drools.service;

import com.liuzhe.drools.dao.RuleDao;
import com.liuzhe.drools.entity.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Alan on 2018/8/10.
 */
@Service
public class RuleService {

    @Resource
    RuleDao ruleDao;


    public Integer setRule(String name, String rule) {
        return ruleDao.setRule(name, rule);
    }

    public Integer deleteRule(int id) {
        return ruleDao.deleteRule(id);
    }

    public Integer updateRule(int id, String name, String rule) {
        return ruleDao.updateRule(id, name, rule);
    }


    @Cacheable(value="rule", key="#id + 'rule'")
    public Rule getRule(int id) {
        //若通过缓存获取，则方法体内的代码不执行
        System.out.println("getRule方法执行了");
        return ruleDao.getById(id);
    }

    /**
     * Cacheable  将返回值缓存，以同样的参数来执行该方法时可以直接从缓存中获取结果，而不需要再次执行该方法
     * 当一个支持缓存的方法在对象内部被调用时是不会触发缓存功能的
     *   value 属性指定Cache名称，cache可以是一个或多个
     *   使用key属性自定义key
     *   condition属性指定发生的条件（例如： condition="#user.id%2==0"）
     */
    @Cacheable(value = "ruleList")
    public List<Rule> getRuleList() {
        System.out.println("getRuleList方法执行了");
        return ruleDao.getRuleList();
    }
}
