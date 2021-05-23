package io.todak.study.javateststudy.study;

import io.todak.study.javateststudy.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
