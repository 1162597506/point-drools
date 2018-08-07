package com.liuzhe.drools.utils;

import com.liuzhe.drools.entity.Point;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by Alan on 2018/8/3.
 */
public class DroolsHelp {

    private static KieContainer getKieContainer() {
        return KieServices.Factory.get().getKieClasspathContainer();
    }

    public static void execute(Point point) {
        KieSession ksession = getKieContainer().newKieSession("PointKS");
        ksession.insert(point);
        ksession.fireAllRules();
        ksession.dispose();
    }

    /**
     * 动态获取KieSession
     *
     * @param rule 规则内容
     * @return KieSession
     */
    public static KieSession getKieSession(String rule) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/rules/rules.drl", rule.getBytes());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            System.out.println(results.getMessages());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        KieBase kieBase = kieContainer.getKieBase();

        return kieBase.newKieSession();
    }


}
