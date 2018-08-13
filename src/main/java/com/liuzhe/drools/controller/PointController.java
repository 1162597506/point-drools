package com.liuzhe.drools.controller;

import com.liuzhe.drools.entity.Point;
import com.liuzhe.drools.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by Alan on 2018/8/3.
 */
@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    PointService pointService;

    @RequestMapping("/excute")
    public Point excute(@RequestBody Point point) {
        Boolean b = new Random().nextBoolean();
        point.setBirthDay(b);
        return  pointService.excute(1,point);
    }

    @RequestMapping("/test")
    public void test() {
        System.out.println("hello  word");
    }
}

// class test {
//
//     public static void main(String[] args) {
//         for (int i = 0; i < 100; i++) {
//             System.out.println("rule buyNumsPoint"+i+"\n" +
//                     "    salience 97\n" +
//                     "    lock-on-active true\n" +
//                     "    when\n" +
//                     "        $point : Point(buyNums == "+i+")\n" +
//                     "    then\n" +
//                     "        $point.setPoint($point.getPoint()+"+10*i+");\n" +
//                     "end");
//         }
//     }
// }