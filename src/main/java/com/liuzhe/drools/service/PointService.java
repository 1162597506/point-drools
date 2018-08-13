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
        //通过drl规则文件，执行规则
        DroolsHelp.execute(point);

        //动态获取规则，规则从数据库中获取并执行规则
        //Rule rule = ruleDao.getById(id);
        //KieSession ks = DroolsHelp.getKieSession(rule.getRule());

        //jmeter测试执行100条规则的执行性能，规则直接写成常量，减少数据库开销
        KieSession ks = DroolsHelp.getKieSession(RULE);
        ks.insert(point);
        ks.fireAllRules();
        return point;
    }


    private static String RULE = "package com.liuzhe.drools\n" +
            "\n" +
            "import com.liuzhe.drools.entity.Point;\n" +
            "\n" +
            "rule birthdayPoint\n" +
            "    // 过生日，则加10分，并且将当月交易比数翻倍后再计算积分\n" +
            "    salience 100\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(birthDay == true)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+10);\n" +
            "        $point.setBuyNums($point.getBuyNums()*2);\n" +
            "        $point.setBuyMoney($point.getBuyMoney()*2);\n" +
            "        $point.setBillThisMonth($point.getBillThisMonth()*2);\n" +
            "//        $point.recordPointLog($point.getUserName(),\"birthdayPoint\");\n" +
            "end\n" +
            "\n" +
            "rule billThisMonthPoint\n" +
            "    // 2018-01-01 - 2018-12-31 信用卡还款3次以上，每满3笔赠送30分\n" +
            "    salience 99\n" +
            "    lock-on-active true\n" +
            "//    date-effective \"01-01-2018\"\n" +
            "//    date-expires \"31-12-2018\"\n" +
            "    when\n" +
            "        $point : Point(billThisMonth >= 3)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+$point.getBillThisMonth()/3*30);\n" +
            "//        $point.recordPointLog($point.getUserName(),\"billThisMonthPoint\");\n" +
            "end\n" +
            "\n" +
            "rule buyMoneyPoint\n" +
            "    // 当月购物总金额100以上，每100元赠送10分\n" +
            "    salience 98\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyMoney >= 100)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+ (int)$point.getBuyMoney()/100 * 10);\n" +
            "//        $point.recordPointLog($point.getUserName(),\"buyMoneyPoint\");\n" +
            "end\n" +
            "\n" +
            "//rule buyNumsPoint\n" +
            "//    // 当月购物次数5次以上，每五次赠送50分\n" +
            "//    salience 97\n" +
            "//    lock-on-active true\n" +
            "//    when\n" +
            "//        $point : Point(buyNums >= 5)\n" +
            "//    then\n" +
            "//        $point.setPoint($point.getPoint()+$point.getBuyNums()/5 * 50);\n" +
            "//end\n" +
            "\n" +
            "rule allFitPoint\n" +
            "    // 特别的，如果全部满足了要求，则额外奖励100分\n" +
            "    salience 96\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point:Point(buyNums >= 5 && billThisMonth >= 3 && buyMoney >= 100)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+ 100);\n" +
            "//        $point.recordPointLog($point.getUserName(),\"allFitPoint\");\n" +
            "end\n" +
            "\n" +
            "rule subBackNumsPoint\n" +
            "    // 发生退货，扣减10分\n" +
            "    salience 10\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(backNums >= 1)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()-10);\n" +
            "//        $point.recordPointLog($point.getUserName(),\"subBackNumsPoint\");\n" +
            "end\n" +
            "\n" +
            "rule subBackMondyPoint\n" +
            "    // 退货金额大于100，扣减100分\n" +
            "    salience 9\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(backMondy >= 100)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()-10);\n" +
            "//        $point.recordPointLog($point.getUserName(),\"subBackMondyPoint\");\n" +
            "end\n" +
            "\n" +
            "\n" +
            "// 当月购物每次赠送10分\n" +
            "rule buyNumsPoint0\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 0)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+0);\n" +
            "end\n" +
            "rule buyNumsPoint1\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 1)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+10);\n" +
            "end\n" +
            "rule buyNumsPoint2\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 2)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+20);\n" +
            "end\n" +
            "rule buyNumsPoint3\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 3)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+30);\n" +
            "end\n" +
            "rule buyNumsPoint4\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 4)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+40);\n" +
            "end\n" +
            "rule buyNumsPoint5\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 5)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+50);\n" +
            "end\n" +
            "rule buyNumsPoint6\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 6)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+60);\n" +
            "end\n" +
            "rule buyNumsPoint7\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 7)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+70);\n" +
            "end\n" +
            "rule buyNumsPoint8\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 8)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+80);\n" +
            "end\n" +
            "rule buyNumsPoint9\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 9)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+90);\n" +
            "end\n" +
            "rule buyNumsPoint10\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 10)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+100);\n" +
            "end\n" +
            "rule buyNumsPoint11\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 11)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+110);\n" +
            "end\n" +
            "rule buyNumsPoint12\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 12)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+120);\n" +
            "end\n" +
            "rule buyNumsPoint13\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 13)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+130);\n" +
            "end\n" +
            "rule buyNumsPoint14\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 14)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+140);\n" +
            "end\n" +
            "rule buyNumsPoint15\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 15)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+150);\n" +
            "end\n" +
            "rule buyNumsPoint16\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 16)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+160);\n" +
            "end\n" +
            "rule buyNumsPoint17\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 17)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+170);\n" +
            "end\n" +
            "rule buyNumsPoint18\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 18)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+180);\n" +
            "end\n" +
            "rule buyNumsPoint19\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 19)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+190);\n" +
            "end\n" +
            "rule buyNumsPoint20\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 20)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+200);\n" +
            "end\n" +
            "rule buyNumsPoint21\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 21)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+210);\n" +
            "end\n" +
            "rule buyNumsPoint22\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 22)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+220);\n" +
            "end\n" +
            "rule buyNumsPoint23\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 23)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+230);\n" +
            "end\n" +
            "rule buyNumsPoint24\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 24)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+240);\n" +
            "end\n" +
            "rule buyNumsPoint25\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 25)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+250);\n" +
            "end\n" +
            "rule buyNumsPoint26\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 26)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+260);\n" +
            "end\n" +
            "rule buyNumsPoint27\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 27)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+270);\n" +
            "end\n" +
            "rule buyNumsPoint28\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 28)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+280);\n" +
            "end\n" +
            "rule buyNumsPoint29\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 29)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+290);\n" +
            "end\n" +
            "rule buyNumsPoint30\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 30)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+300);\n" +
            "end\n" +
            "rule buyNumsPoint31\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 31)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+310);\n" +
            "end\n" +
            "rule buyNumsPoint32\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 32)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+320);\n" +
            "end\n" +
            "rule buyNumsPoint33\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 33)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+330);\n" +
            "end\n" +
            "rule buyNumsPoint34\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 34)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+340);\n" +
            "end\n" +
            "rule buyNumsPoint35\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 35)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+350);\n" +
            "end\n" +
            "rule buyNumsPoint36\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 36)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+360);\n" +
            "end\n" +
            "rule buyNumsPoint37\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 37)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+370);\n" +
            "end\n" +
            "rule buyNumsPoint38\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 38)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+380);\n" +
            "end\n" +
            "rule buyNumsPoint39\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 39)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+390);\n" +
            "end\n" +
            "rule buyNumsPoint40\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 40)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+400);\n" +
            "end\n" +
            "rule buyNumsPoint41\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 41)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+410);\n" +
            "end\n" +
            "rule buyNumsPoint42\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 42)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+420);\n" +
            "end\n" +
            "rule buyNumsPoint43\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 43)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+430);\n" +
            "end\n" +
            "rule buyNumsPoint44\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 44)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+440);\n" +
            "end\n" +
            "rule buyNumsPoint45\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 45)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+450);\n" +
            "end\n" +
            "rule buyNumsPoint46\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 46)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+460);\n" +
            "end\n" +
            "rule buyNumsPoint47\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 47)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+470);\n" +
            "end\n" +
            "rule buyNumsPoint48\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 48)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+480);\n" +
            "end\n" +
            "rule buyNumsPoint49\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 49)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+490);\n" +
            "end\n" +
            "rule buyNumsPoint50\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 50)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+500);\n" +
            "end\n" +
            "rule buyNumsPoint51\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 51)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+510);\n" +
            "end\n" +
            "rule buyNumsPoint52\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 52)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+520);\n" +
            "end\n" +
            "rule buyNumsPoint53\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 53)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+530);\n" +
            "end\n" +
            "rule buyNumsPoint54\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 54)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+540);\n" +
            "end\n" +
            "rule buyNumsPoint55\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 55)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+550);\n" +
            "end\n" +
            "rule buyNumsPoint56\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 56)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+560);\n" +
            "end\n" +
            "rule buyNumsPoint57\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 57)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+570);\n" +
            "end\n" +
            "rule buyNumsPoint58\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 58)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+580);\n" +
            "end\n" +
            "rule buyNumsPoint59\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 59)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+590);\n" +
            "end\n" +
            "rule buyNumsPoint60\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 60)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+600);\n" +
            "end\n" +
            "rule buyNumsPoint61\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 61)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+610);\n" +
            "end\n" +
            "rule buyNumsPoint62\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 62)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+620);\n" +
            "end\n" +
            "rule buyNumsPoint63\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 63)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+630);\n" +
            "end\n" +
            "rule buyNumsPoint64\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 64)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+640);\n" +
            "end\n" +
            "rule buyNumsPoint65\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 65)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+650);\n" +
            "end\n" +
            "rule buyNumsPoint66\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 66)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+660);\n" +
            "end\n" +
            "rule buyNumsPoint67\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 67)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+670);\n" +
            "end\n" +
            "rule buyNumsPoint68\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 68)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+680);\n" +
            "end\n" +
            "rule buyNumsPoint69\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 69)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+690);\n" +
            "end\n" +
            "rule buyNumsPoint70\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 70)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+700);\n" +
            "end\n" +
            "rule buyNumsPoint71\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 71)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+710);\n" +
            "end\n" +
            "rule buyNumsPoint72\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 72)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+720);\n" +
            "end\n" +
            "rule buyNumsPoint73\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 73)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+730);\n" +
            "end\n" +
            "rule buyNumsPoint74\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 74)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+740);\n" +
            "end\n" +
            "rule buyNumsPoint75\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 75)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+750);\n" +
            "end\n" +
            "rule buyNumsPoint76\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 76)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+760);\n" +
            "end\n" +
            "rule buyNumsPoint77\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 77)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+770);\n" +
            "end\n" +
            "rule buyNumsPoint78\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 78)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+780);\n" +
            "end\n" +
            "rule buyNumsPoint79\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 79)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+790);\n" +
            "end\n" +
            "rule buyNumsPoint80\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 80)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+800);\n" +
            "end\n" +
            "rule buyNumsPoint81\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 81)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+810);\n" +
            "end\n" +
            "rule buyNumsPoint82\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 82)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+820);\n" +
            "end\n" +
            "rule buyNumsPoint83\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 83)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+830);\n" +
            "end\n" +
            "rule buyNumsPoint84\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 84)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+840);\n" +
            "end\n" +
            "rule buyNumsPoint85\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 85)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+850);\n" +
            "end\n" +
            "rule buyNumsPoint86\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 86)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+860);\n" +
            "end\n" +
            "rule buyNumsPoint87\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 87)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+870);\n" +
            "end\n" +
            "rule buyNumsPoint88\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 88)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+880);\n" +
            "end\n" +
            "rule buyNumsPoint89\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 89)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+890);\n" +
            "end\n" +
            "rule buyNumsPoint90\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 90)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+900);\n" +
            "end\n" +
            "rule buyNumsPoint91\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 91)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+910);\n" +
            "end\n" +
            "rule buyNumsPoint92\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 92)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+920);\n" +
            "end\n" +
            "rule buyNumsPoint93\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 93)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+930);\n" +
            "end\n" +
            "rule buyNumsPoint94\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 94)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+940);\n" +
            "end\n" +
            "rule buyNumsPoint95\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 95)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+950);\n" +
            "end\n" +
            "rule buyNumsPoint96\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 96)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+960);\n" +
            "end\n" +
            "rule buyNumsPoint97\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 97)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+970);\n" +
            "end\n" +
            "rule buyNumsPoint98\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 98)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+980);\n" +
            "end\n" +
            "rule buyNumsPoint99\n" +
            "    salience 97\n" +
            "    lock-on-active true\n" +
            "    when\n" +
            "        $point : Point(buyNums == 99)\n" +
            "    then\n" +
            "        $point.setPoint($point.getPoint()+990);\n" +
            "end";
}
