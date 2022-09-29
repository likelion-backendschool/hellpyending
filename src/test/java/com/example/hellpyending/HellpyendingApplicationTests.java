//package com.example.hellpyending;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//@SpringBootTest
//class HellpyendingApplicationTests {
//
//    @Test
//    void contextLoads() {
//    }
//
//
//    @Test
//    void random(){
//        Random rand = new Random();
//        ArrayList<Integer> price = new ArrayList<>();
//
//        final int[] month_1= {30000,20000,50000,45000,65000,25000};
//
//        for (int i = 0 ; i<10 ; i++){
//            int price_1 = month_1[rand.nextInt(5)];
//
//            System.out.println("1개월"+price_1);
//            System.out.println("3개월"+price_1*3);
//            System.out.println("6개월"+(int) ((price_1*6)*0.9));
//            System.out.println("12개월"+(int) ((price_1*12)*0.8));
//
//        }
//    }
//}
