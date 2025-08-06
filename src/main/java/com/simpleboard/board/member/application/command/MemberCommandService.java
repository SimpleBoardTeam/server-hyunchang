package com.simpleboard.board.member.application.command;

import com.simpleboard.board.member.application.event.MemberUpdateNicknameEvent;
import com.simpleboard.board.member.application.event.MemberWithdrawnEvent;
import com.simpleboard.board.member.application.exception.MemberNotFoundException;
import com.simpleboard.board.member.domain.Member;
import com.simpleboard.board.member.domain.NicknameDuplicationChecker;
import com.simpleboard.board.member.domain.repository.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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