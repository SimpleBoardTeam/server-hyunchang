package com.simpleboard.board.member.application.command;

import com.simpleboard.board.member.application.event.MemberUpdateNicknameEvent;
import com.simpleboard.board.member.application.event.MemberWithdrawnEvent;
import com.simpleboard.board.member.application.exception.MemberNotFoundException;
import com.simpleboard.board.member.domain.Member;
import com.simpleboard.board.member.domain.NicknameDuplicationChecker;
import com.simpleboard.board.member.domain.repository.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * <p>회원 도메인에 대한 Command 유스케이스를 처리하는 Application Service.</p>
 *
 * <ul>
 *   <li>회원 탈퇴 처리</li>
 *   <li>회원 닉네임 변경 처리</li>
 *   <li>처리 후 관련 도메인 이벤트 발행</li>
 * </ul>
 *
 * @domain application-service
 * @transactional
 */
@Service
public class MemberCommandService {

  private final MemberRepository memberRepository;
  private final ApplicationEventPublisher eventPublisher;
  private final NicknameDuplicationChecker nicknameDuplicationChecker;

  public MemberCommandService(
      MemberRepository memberRepository,
      ApplicationEventPublisher eventPublisher
  ) {
    this.memberRepository = memberRepository;
    this.eventPublisher = eventPublisher;
    this.nicknameDuplicationChecker = new NicknameDuplicationChecker(memberRepository);
  }

  public void withdrawMemberById(Long memberId) {
    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

    member.withdraw();
    memberRepository.save(member);

    eventPublisher.publishEvent(new MemberWithdrawnEvent(memberId));
  }

  public void updateNickname(Long memberId, String newNickname) {
    Member member =
        memberRepository
            .findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

    nicknameDuplicationChecker.checkDuplicated(newNickname);
    member.updateNickname(newNickname);
    memberRepository.save(member);

    eventPublisher.publishEvent(new MemberUpdateNicknameEvent(memberId, newNickname));
  }
}