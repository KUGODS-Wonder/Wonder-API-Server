package kugods.wonder.app.reservation.repository;

import kugods.wonder.app.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findOneByMember_EmailAndVoluntaryWork_VoluntaryWorkId(String email, Long voluntaryWorkId);
}
