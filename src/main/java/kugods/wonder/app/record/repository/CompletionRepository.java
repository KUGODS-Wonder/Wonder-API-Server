package kugods.wonder.app.record.repository;

import kugods.wonder.app.record.entity.Completion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompletionRepository extends JpaRepository<Completion, Long> {
    Optional<Completion> findOneByMember_EmailAndWalk_WalkId(String email, Long walkId);
}
