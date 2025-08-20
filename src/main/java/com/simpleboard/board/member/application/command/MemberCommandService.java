package com.simpleboard.board.member.application.command;

import com.simpleboard.board.member.application.command.dto.MemberRegisterCommand;
import com.simpleboard.board.member.application.command.event.MemberUpdateNicknameEvent;
import com.simpleboard.board.member.application.command.event.MemberWithdrawnEvent;
import com.simpleboard.board.member.application.exception.MemberNotFoundException;
import com.simpleboard.board.member.domain.BirthYear;
import com.simpleboard.board.member.domain.Member;
import com.simpleboard.board.member.domain.Nickname;
import com.simpleboard.board.member.domain.NicknamePolicy;
import com.simpleboard.board.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 회원 도메인에 대한 Command 유스케이스를 처리하는 Application Service.
 *
 * <ul>
 *   <li>회원 가입 처리
 *   <li>회원 탈퇴 처리
 *   <li>회원 닉네임 변경 처리
 *   <li>처리 후 관련 도메인 이벤트 발행
 * </ul>
 *
 * @domain application-service
 * @transactional
 */
@Service
@RequiredArgsConstructor
public class MemberCommandService {

  private final MemberRepository memberRepository;
  private final ApplicationEventPublisher eventPublisher;
  private final NicknamePolicy nicknamePolicy;

  public void registerMember(MemberRegisterCommand command) {
    Member member =
        Member.create(
            command.memberId(),
            Nickname.of(command.nickname()),
            command.gender(),
            BirthYear.of(command.birthYear()),
            nicknamePolicy);

    memberRepository.save(member);
  }

  public void withdrawMemberById(Long memberId) {
    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

    member.withdraw();
    memberRepository.save(member);

    eventPublisher.publishEvent(new MemberWithdrawnEvent(memberId));
  }

  public void updateNickname(Long memberId, String newNickname) {
    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

    member.updateNickname(Nickname.of(newNickname), nicknamePolicy);
    memberRepository.save(member);

    eventPublisher.publishEvent(new MemberUpdateNicknameEvent(memberId, newNickname));
  }
}
