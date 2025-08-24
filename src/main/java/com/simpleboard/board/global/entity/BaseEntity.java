package com.simpleboard.board.global.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <b>공통 JPA 엔티티</b> Entity(non‑root)
 *
 * <p>생성, 수정 시간 컬럼을 자동으로 처리해주는 JPA 공통 엔티티 클래스
 *
 * <p>createdAt, updatedAt을 사용하는 JPA 엔티티는 해당 클래스를 extends 하여 구현한다.
 *
 * @since 1.0
 */
@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @CreatedDate
  @Column(updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime updatedAt;
}
