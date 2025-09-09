package com.simpleboard.board.member.infrastructure.jpa.mapper;

import com.simpleboard.board.member.domain.Member;
import com.simpleboard.board.member.infrastructure.jpa.entity.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

  public MemberEntity toEntity(Member member) {
    return new MemberEntity(
        member.getMemberId(), // id
        member.getNickname(), // Nickname (VO)
        member.getGender(), // Gender (Enum)
        member.getBirthYear(), // BirthYear (VO)
        member.getSignUpDate(), // LocalDateTime
        member.isWithdrawn() // boolean
        );
  }

  public Member toDomain(MemberEntity entity) {
    return Member.reconstruct(
        entity.getId(),
        entity.getNickname(),
        entity.getGender(),
        entity.getBirthYear(),
        entity.getSignUpDate(),
        entity.isWithdrawn());
  }
}
