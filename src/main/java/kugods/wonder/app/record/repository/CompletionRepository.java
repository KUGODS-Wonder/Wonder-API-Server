package kugods.wonder.app.record.repository;

import kugods.wonder.app.record.entity.Completion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletionRepository extends JpaRepository<Completion, Long> {
}
