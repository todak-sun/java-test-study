package io.todak.study.javateststudy.study;

import io.todak.study.javateststudy.domain.Member;
import io.todak.study.javateststudy.domain.Study;
import io.todak.study.javateststudy.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;

    StudyService studyService;


    @Test
    public void createStudyService() {
        studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @DisplayName("Stubbing을 사용한 테스트")
    @Test
    public void createStudyServiceWithStubbing() {
        studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        String email = "tsjdydwn@gmail.com";

        Member member = new Member();
        member.setId(1L);
        member.setEmail(email);

        when(memberService.findById(1L))
                .thenReturn(Optional.of(member));

        Study study = new Study(10, "java");

        Optional<Member> optionalMember = memberService.findById(1L);
        assertEquals(email, optionalMember.get().getEmail());
    }

    @DisplayName("Stubbing을 사용한 테스트 - with out return type")
    @Test
    public void validateTest() {
        doThrow(new IllegalArgumentException())
                .when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });
    }

    @DisplayName("Stubbing 여러번")
    @Test
    public void stubbing_again() {

        long id = 1L;
        String email = "tsjdydwn@gmail.com";

        Member member = new Member();
        member.setId(id);
        member.setEmail(email);

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        // 첫번째 호출
        Optional<Member> optionalMember = memberService.findById(id);
        assertEquals(member.getEmail(), optionalMember.get().getEmail());
        assertEquals(member.getId(), optionalMember.get().getId());

        // 두번째 호출
        assertThrows(RuntimeException.class, () -> {
            memberService.findById(id);
        });

        // 세번째 호출
        Optional<Member> optionalMember3 = memberService.findById(id);
        assertTrue(optionalMember3.isEmpty());
    }

    @DisplayName("연습문제[1]")
    @Test
    public void createNewStudy_example() {

        studyService = new StudyService(memberService, studyRepository);

        Study study = new Study(10, "테스트");
        long memberId = 1L;

        //TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 member 객체를 리턴하자.
        Member member = new Member();
        member.setEmail("tjsdydwn@gmail.com");
        member.setId(memberId);
        when(memberService.findById(memberId))
                .thenReturn(Optional.of(member));

        //TODO studyRepository 객체에 save 메서드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 stubbing
        when(studyRepository.save(study))
                .thenReturn(study);

        studyService.createNewStudy(memberId, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());
    }

    @DisplayName("mock 객체 확인해보기")
    @Test
    public void check_mock_instance() {
        studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Long memberId = 1L;

        Member member = new Member();
        member.setEmail("tjsdydwn@gmail.com");
        member.setId(memberId);

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertEquals(member, study.getOwner());

        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(any());

        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);

        verifyNoMoreInteractions(memberService);
    }

}