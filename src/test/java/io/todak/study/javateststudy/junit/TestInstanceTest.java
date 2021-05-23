package io.todak.study.javateststudy.junit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestInstanceTest {

    int value = 1;

    @Test
    public void test() {
        System.out.println(value++);
    }

    @Test
    public void test2() {
        System.out.println(value++);
    }

}
