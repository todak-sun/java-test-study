package io.todak.study.javateststudy.study;

import io.todak.study.javateststudy.domain.Member;
import io.todak.study.javateststudy.member.MemberRepository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
public class StudyServiceTestWithTestcontainer {

    private static Logger log = LoggerFactory.getLogger(StudyServiceTestWithTestcontainer.class);

    @Container
    private static GenericContainer genericContainer;

    static {
        genericContainer = new GenericContainer("postgres:latest")
                .withEnv("POSTGRES_DB", "studytest")
                .withEnv("POSTGRES_USER", "todak")
                .withEnv("POSTGRES_PASSWORD", "todak");
        genericContainer.setPortBindings(List.of("15432:5432"));
    }

    @BeforeAll
    static void beforeAll() {
        genericContainer.start();
    }

    @AfterAll
    static void afterAll() {
        genericContainer.stop();
    }

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        Integer mappedPort = genericContainer.getMappedPort(5432);
        log.info("mappedPort : {}", mappedPort);
        log.info("exposedPorts : {}", genericContainer.getExposedPorts());
        log.info("env : {}", genericContainer.getEnvMap());

        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        genericContainer.followOutput(logConsumer);
    }

    @Test
    public void test() {

        //given
        Member member = new Member();
        member.setEmail("tjsdydwn@gmail.com");

        //when
        Member newMember = memberRepository.save(member);

        //then
        assertNotNull(newMember);
        assertEquals(member.getEmail(), newMember.getEmail());
        assertTrue(Hibernate.isInitialized(newMember));
    }

}
