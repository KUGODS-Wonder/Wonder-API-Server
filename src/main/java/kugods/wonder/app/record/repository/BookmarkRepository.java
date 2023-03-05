package kugods.wonder.app.record.repository;

import kugods.wonder.app.record.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
