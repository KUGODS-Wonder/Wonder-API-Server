package kugods.wonder.app.reservation.entity;

import kugods.wonder.app.common.entity.BaseEntity;
import kugods.wonder.app.reservation.dto.VoluntaryWorkResponse;
import kugods.wonder.app.walk.entity.Walk;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String description;

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
                .build();
    }

}
