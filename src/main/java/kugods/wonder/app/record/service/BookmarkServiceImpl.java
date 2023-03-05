package kugods.wonder.app.record.service;

import kugods.wonder.app.auth.exception.MemberDoesNotExistException;
import kugods.wonder.app.member.entity.Member;
import kugods.wonder.app.member.repository.MemberRepository;
import kugods.wonder.app.record.dto.BookmarkRequest;
import kugods.wonder.app.record.dto.BookmarkResponse;
import kugods.wonder.app.record.entity.Bookmark;
import kugods.wonder.app.record.exception.DuplicatedBookmarkException;
import kugods.wonder.app.record.repository.BookmarkRepository;
import kugods.wonder.app.walk.entity.Walk;
import kugods.wonder.app.walk.exception.BookmarkDoesNotExistException;
import kugods.wonder.app.walk.exception.WalkDoesNotExistException;
import kugods.wonder.app.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService{

    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final WalkRepository walkRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookmarkResponse> getBookmarks(Long memberId) {
        return bookmarkRepository.findAllByMemberId(memberId)
                .stream()
                .map(Bookmark::toReponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookmarkResponse addBookmark(BookmarkRequest request) {
        validateBookmarkDuplication(request.getMemberId(), request.getWalkId());
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(MemberDoesNotExistException::new);
        Walk walk = walkRepository.findById(request.getWalkId())
                .orElseThrow(WalkDoesNotExistException::new);
        Bookmark bookmark = Bookmark.builder()
                .title(request.getTitle())
                .content(request.getContents())
                .member(member)
                .walk(walk)
                .build();
        bookmarkRepository.save(bookmark);

        return BookmarkResponse.builder()
                .bookmarkId(bookmark.getBookmarkId())
                .title(bookmark.getTitle())
                .contents(bookmark.getContent())
                .build();
    }

    @Override
    @Transactional
    public BookmarkResponse deleteBookmark(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(BookmarkDoesNotExistException::new);
        bookmarkRepository.delete(bookmark);

        return BookmarkResponse.builder()
                .bookmarkId(bookmark.getBookmarkId())
                .title(bookmark.getTitle())
                .contents(bookmark.getContent())
                .build();
    }

    private void validateBookmarkDuplication(Long memberId, Long walkId) {
        boolean isDuplicated = bookmarkRepository.findOneByWalkIdAndMemberId(walkId, memberId).isPresent();
        if (isDuplicated) {
            throw new DuplicatedBookmarkException();
        }
    }
}
