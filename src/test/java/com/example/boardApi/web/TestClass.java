package com.example.boardApi.web;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
public class TestClass {

    @BeforeEach
    @DisplayName("BeforeEach")
    void setUp11() {
        System.out.println("BeforeEach");
    }

    @Test
    @DisplayName("Test")
    void test() {
        System.out.println("Test");
    }


    @Test
    void test_And_dd() {
        System.out.println("Test");
    }

    @Test
    @DisplayName("null")
    void same() {
        String str = "luv2code";
        Assertions.assertSame("","","Good");
        //Assertions.assertArrayEquals(new String[];
    }
}
