package io.todak.study.javateststudy.junit;

import io.todak.study.javateststudy.annotation.SlowTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

// @ExtendWith(FindSlowTestExtention.class)
public class TestWithExtension {

    @RegisterExtension
    static FindSlowTestExtention findSlowTestExtention = new FindSlowTestExtention();

    @Test
    public void slowTest() throws InterruptedException {
        Thread.sleep(1001);
    }

    @SlowTest
    public void slowTestWithAnnotation() throws Exception {
        Thread.sleep(1001);
    }

    @Test
    public void fastTest() {

    }


}
