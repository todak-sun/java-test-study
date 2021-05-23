package io.todak.study.javateststudy.study;

import io.todak.study.javateststudy.domain.Member;
import io.todak.study.javateststudy.domain.Study;
import io.todak.study.javateststudy.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

//    @Mock
//    MemberService memberService;
//    @Mock
//    StudyRepository studyRepository;

    StudyService studyService;

    @Test
    public void createStudyService(@Mock MemberService memberService,
                                   @Mock StudyRepository studyRepository) {
        studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    public void test2() {

        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);

        // stubbing
        Member member = new Member();
        member.setId(1L);
        member.setEmail("tsjdydwn@gmail.com");
        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        Study study = new Study(10, "java");

    }

}