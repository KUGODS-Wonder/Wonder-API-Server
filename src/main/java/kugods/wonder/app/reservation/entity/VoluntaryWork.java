package kugods.wonder.app.reservation.entity;

import kugods.wonder.app.reservation.dto.VoluntaryWorkResponse;
import kugods.wonder.app.reservation.exception.CurrentPeopleNumberIsZeroException;
import kugods.wonder.app.reservation.exception.ExceedMaxPeopleNumberException;
import kugods.wonder.app.walk.entity.Walk;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VoluntaryWork")
@Entity
public class VoluntaryWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voluntary_work_id")
    private Long voluntaryWorkId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_id")
    private Walk walk;

    @Column(nullable = false)
    private String specialTheme;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private String specificAddress;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Integer currentPeopleNumber = 0;

    @Column(nullable = false)
    private Integer maxPeopleNumber;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active = true;

    public VoluntaryWorkResponse toResponse() {
        return VoluntaryWorkResponse.builder()
                .voluntaryWorkId(getVoluntaryWorkId())
                .walkId(getWalk().getWalkId())
                .specialTheme(specialTheme)
                .institution(institution)
                .specificAddress(specificAddress)
                .startDate(startDate)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public void reserve() {
        if (this.currentPeopleNumber >= this.maxPeopleNumber) {
            throw new ExceedMaxPeopleNumberException();
        }
        this.currentPeopleNumber++;
    }

    public void cancel() {
        if (this.currentPeopleNumber <= 0) {
            throw new CurrentPeopleNumberIsZeroException();
        }
        this.currentPeopleNumber--;
    }

}
