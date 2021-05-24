package io.todak.study.javateststudy.domain;

import io.todak.study.javateststudy.study.StudyStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Study {
    @Id
    @GeneratedValue
    private Long id;
    private StudyStatus status = StudyStatus.DRAFT;
    private int limitCount;
    private String name;
    private LocalDateTime openedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member owner;

    protected Study() {
    }

    public Study(int limit, String name) {
        this.limitCount = limit;
        this.name = name;
    }

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 커야 한다.");
        }
        this.limitCount = limit;
    }

    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.DRAFT;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public StudyStatus getStatus() {
        return status;
    }

    public int getLimitCount() {
        return limitCount;
    }
}
