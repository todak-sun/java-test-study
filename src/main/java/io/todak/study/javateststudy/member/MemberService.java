package io.todak.study.javateststudy.member;

import io.todak.study.javateststudy.domain.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> findById(Long memberId) throws MemberNotFoundException;
}
