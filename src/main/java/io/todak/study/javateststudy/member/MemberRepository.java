package io.todak.study.javateststudy.member;

import io.todak.study.javateststudy.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
