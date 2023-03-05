package kugods.wonder.app.member.repository;

import kugods.wonder.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findOneByEmail(String email);

    Optional<Member> findOneByName(String name);
}
