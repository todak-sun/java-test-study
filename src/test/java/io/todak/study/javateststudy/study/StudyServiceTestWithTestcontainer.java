package io.todak.study.javateststudy.study;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class StudyServiceTestWithTestcontainer {

    private static Logger log = LoggerFactory.getLogger(StudyServiceTestWithTestcontainer.class);

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("studytest")
            .withEnv("POSTGRES_DB", "studytest")
            ;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();
        log.info("jdbcUrl : {} ", jdbcUrl);
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    public void test() {

    }

}
