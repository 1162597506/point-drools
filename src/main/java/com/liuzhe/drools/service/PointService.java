package com.liuzhe.drools.service;

import com.liuzhe.drools.dao.RuleDao;
import com.liuzhe.drools.entity.Point;
import com.liuzhe.drools.entity.Rule;
import com.liuzhe.drools.utils.DroolsHelp;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Alan on 2018/8/3.
 */
@Service
public class PointService {

    @Autowired
    RuleDao ruleDao;

    public Point excute(int id, Point point) {
//        //通过规则文件，执行规则
//        DroolsHelp.execute(point);

        //动态获取规则，并执行规则
        Rule rule = ruleDao.getById(id);
        KieSession ks = DroolsHelp.getKieSession(rule.getRule());
        ks.insert(point);
        ks.fireAllRules();
        return point;
    }
}
