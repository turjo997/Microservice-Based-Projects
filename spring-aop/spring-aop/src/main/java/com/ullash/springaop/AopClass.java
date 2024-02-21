package com.ullash.springaop;

import org.springframework.stereotype.Component;

@Component
public class AopClass {

    public void hello() {
        System.out.println("Hello World");
    }

    public void printSomething(String something) {
        System.out.println(something);
    }

    public int sum(int a, int b) {
        return a+b;
    }


}
