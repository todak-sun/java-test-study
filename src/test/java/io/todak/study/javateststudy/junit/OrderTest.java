package io.todak.study.javateststudy.junit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderTest {

    private final static Logger log = LoggerFactory.getLogger(OrderTest.class);

    @Test
    @Order(1)
    public void test1() {
        log.info("test1");
    }

    @Test
    @Order(3)
    public void test3() {
        log.info("test3");
    }

    @Test
    @Order(2)
    public void test2() {
        log.info("test2");
    }

}
