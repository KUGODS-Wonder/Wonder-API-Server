package kugods.wonder.app.record.service;

import kugods.wonder.app.member.exception.MemberDoesNotExistException;
import kugods.wonder.app.member.entity.Member;
import kugods.wonder.app.member.repository.MemberRepository;
import kugods.wonder.app.record.dto.CompletionRequest;
import kugods.wonder.app.record.dto.CompletionResponse;
import kugods.wonder.app.record.entity.Completion;
import kugods.wonder.app.record.exception.DuplicatedBookmarkException;
import kugods.wonder.app.record.repository.CompletionRepository;
import kugods.wonder.app.walk.entity.Walk;
import kugods.wonder.app.walk.exception.WalkDoesNotExistException;
import kugods.wonder.app.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompletionServiceImpl implements CompletionService{

    private final CompletionRepository completionRepository;
    private final MemberRepository memberRepository;
    private final WalkRepository walkRepository;

    @Override
    public CompletionResponse addCompletion(String email, CompletionRequest request) {
        validateCompletionDuplication(email, request.getWalkId());
        Member member = memberRepository.findOneByEmail(email)
                .orElseThrow(MemberDoesNotExistException::new);
        Walk walk = walkRepository.findById(request.getWalkId())
                .orElseThrow(WalkDoesNotExistException::new);
        Completion completion = Completion.builder()
                .timeRecord(request.getTimeRecord())
                .member(member)
                .walk(walk)
                .build();
        completionRepository.save(completion);

        return completion.toResponse();
    }

    private void validateCompletionDuplication(String email, Long walkId) {
        boolean isDuplicated = completionRepository.findOneByMember_EmailAndWalk_WalkId(email, walkId).isPresent();
        if (isDuplicated) {
            throw new DuplicatedBookmarkException();
        }
    }
}
