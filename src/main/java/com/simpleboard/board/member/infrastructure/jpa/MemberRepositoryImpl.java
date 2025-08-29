package com.simpleboard.board.member.infrastructure.jpa;

import com.simpleboard.board.member.domain.Member;
import com.simpleboard.board.member.domain.repository.MemberRepository;
import com.simpleboard.board.member.infrastructure.jpa.entity.MemberEntity;
import com.simpleboard.board.member.infrastructure.jpa.mapper.MemberMapper;
import com.simpleboard.board.member.infrastructure.jpa.repository.MemberEntityRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

  private final MemberEntityRepository memberEntityRepository;
  private final MemberMapper memberMapper;

  @Override
  public Boolean existsByNickname(String nickname) {
    return memberEntityRepository.existsByNicknameValue(nickname);
  }

  @Override
  public Optional<Member> findById(Long memberId) {
    return memberEntityRepository.findById(memberId).map(memberMapper::toDomain);
  }

  @Override
  public void save(Member member) {
    MemberEntity entity = memberMapper.toEntity(member);
    memberEntityRepository.save(entity);
  }
}
