package kugods.wonder.app.reservation.service;

import kugods.wonder.app.reservation.dto.VoluntaryWorkResponse;
import kugods.wonder.app.reservation.entity.VoluntaryWork;
import kugods.wonder.app.reservation.repository.ReservationRepository;
import kugods.wonder.app.reservation.repository.VoluntaryWorkRepository;
import kugods.wonder.app.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final WalkRepository walkRepository;
    private final VoluntaryWorkRepository voluntaryWorkRepository;

    @Override
    public List<VoluntaryWorkResponse> getVoluntaryWorkList() {
        return voluntaryWorkRepository.findAll()
                .stream()
                .filter(VoluntaryWork::isActive)
                .map(VoluntaryWork::toResponse)
                .collect(Collectors.toList());
    }
}
