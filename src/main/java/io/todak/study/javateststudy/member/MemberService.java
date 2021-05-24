package io.todak.study.javateststudy.member;

import io.todak.study.javateststudy.domain.Member;
import io.todak.study.javateststudy.domain.Study;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);

}
