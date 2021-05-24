package io.todak.study.javateststudy.junit;

import io.todak.study.javateststudy.annotation.FastTest;
import io.todak.study.javateststudy.annotation.SlowTest;
import io.todak.study.javateststudy.domain.Study;
import io.todak.study.javateststudy.study.StudyStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    public static Logger log = LoggerFactory.getLogger(StudyTest.class);

    @BeforeAll
    public static void beforeAll() {
        // 모든 테스트를 실행하기 전 딱 한 번.
        // static으로 작성해야 함.
        log.info("beforeAll");
    }

    @AfterAll
    public static void afterAll() {
        // 모든 테스트를 실행한 후 딱 한 번.
        // static으로 작성해야 함.
        log.info("afterAll");
    }

    @BeforeEach
    public void beforeEach() {
        //각각의 테스트를 실행하기 전
        log.info("beforeEach");
    }

    @AfterEach
    public void afterEach() {
        //각각의 테스트를 실행한 후.
        log.info("afterEach");
    }


    /**
     * 1) Assrtions 메서드의 메시지를 람다로 작성하는 이유
     * 메시지를 람다를 통해 작성하게 되면, 꼭 필요한 경우 람다의 메서드가 실행되며
     * 문자열이 연산된다.
     * 그냥 문자열을 사용할 경우, 무조건 연산한다.
     */

    /**
     * 2) assertAll
     * 내부에 있는 테스트의 통과여부를 모두 한 번에 알 수 있다.
     */
    @DisplayName("스터디를 만들면, 처음 상태는 DRAFT여야 한다.")
    @Test
    public void create_test() {
        log.info("test");
        Study study = new Study(11);

        assertAll(
                () -> assertNotNull(study),
                () -> Assertions.assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimitCount() > 0,
                        () -> "스터디 최대 참석 가능인원은 항상 0보다 커야한다."));
    }

    @DisplayName("timeout")
    @Test
    public void timeout() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(101);
            // assertTimeoutPreemptively
            // 안의 코드는
            // 별도의 Thread를 사용하기 때문에,
            // Transaction을 사용하는 메서드의 경우
            // 사이드 이펙트가 발생할 수 있다.
        });
    }

    @DisplayName("조건에 따라 실행 -> assumming")
    @Test
    public void assumming() {
        int a = 3;
        assumeTrue(a == 3);
        assertTrue(true);
    }

    @DisplayName("조건에 따라 실행2")
    @Test
    public void assummingThat() {
        assumingThat(OS.WINDOWS.isCurrentOs(), () -> {
            assertTrue(true);
        });
    }

    @DisplayName("반복테스트")
    @RepeatedTest(value = 5, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    public void repeatTest(RepetitionInfo info) {
        log.info("test : {}/{}", info.getCurrentRepetition(), info.getTotalRepetitions());
    }

    @DisplayName("파라미터 테스트")
    @ParameterizedTest(name = "{index} {displayName} value={0}")
    @ValueSource(strings = {"안", "녕", "하", "세", "요"})
    void parameterizedTest(String value) {
        log.info("value : {}", value);
    }


    @DisplayName("name에 변수를 담자")
    @ParameterizedTest(name = "{index} {displayName} name={0}")
    @ValueSource(strings = {"이름", "이름2", "이름3"})
    public void crate_test_2(String name) {
        log.info("test2 : {}", name);
    }

    @Disabled
    @Test
    public void test3() {
        log.info("disabled");
    }

    @SlowTest
    public void slowtest() {
        log.info("slowtest");
    }

    @FastTest
    public void fasttest() {
        log.info("fasttest");
    }

}