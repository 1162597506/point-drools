package com.liuzhe.drools;

import com.liuzhe.drools.entity.Rule;

/**
 * Created by Alan on 2018/8/13.
 */
public class T {

    public static void main(String[] args) {
        System.out.println(intFinally());
        System.out.println(objectFinally().getId());
    }

    public static int intFinally(){
        try {
            return 1;
        }finally {
            return 2;
        }
    }

    public static Rule objectFinally(){
        Rule rule1 = new Rule(1,"1","1");
        Rule rule2 = new Rule(2,"2","2");
        try {
            return rule1;
        }finally {
            return rule2;
        }
    }
}