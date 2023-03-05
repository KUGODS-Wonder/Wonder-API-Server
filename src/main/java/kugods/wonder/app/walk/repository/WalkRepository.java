package kugods.wonder.app.walk.repository;

import kugods.wonder.app.walk.entity.Walk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkRepository extends JpaRepository<Walk, Long> {
}
