package kugods.wonder.app.record.repository;

import kugods.wonder.app.record.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findOneByWalk_WalkIdAndMember_MemberId(Long walkId, Long memberId);

    List<Bookmark> findAllByMember_MemberId(Long memberId);
}
