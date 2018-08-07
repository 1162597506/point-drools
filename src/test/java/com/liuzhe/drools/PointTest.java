package com.liuzhe.drools;

import com.liuzhe.drools.controller.PointController;
import com.liuzhe.drools.entity.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Alan on 2018/8/3.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PointTest {

    @Autowired
    PointController controller;

    @Test
    public void point() {
        Point point = new Point();
        point.setUserName("ko");
        point.setBillThisMonth(3);
        point.setBackMondy(100d);
        point.setBuyMoney(500d);
        point.setBackNums(1);
        point.setBuyNums(5);
        point.setBirthDay(true);
        point.setPoint(0l);
        System.out.println(controller.excute(point));
    }

}
