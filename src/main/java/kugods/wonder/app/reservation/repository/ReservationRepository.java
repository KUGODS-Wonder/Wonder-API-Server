package kugods.wonder.app.reservation.repository;

import kugods.wonder.app.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
