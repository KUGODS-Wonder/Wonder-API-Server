package kugods.wonder.app.record.entity;

import kugods.wonder.app.common.entity.BaseEntity;
import kugods.wonder.app.member.entity.Member;
import kugods.wonder.app.record.dto.CompletionResponse;
import kugods.wonder.app.walk.entity.Walk;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Completion")
@Entity
public class Completion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "completion_id")
    private Long completionId;

    @Column(name = "time_record")
    private int timeRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_id")
    private Walk walk;

    public CompletionResponse toResponse() {
        return CompletionResponse.builder()
                .completionId(getCompletionId())
                .memberId(getMember().getMemberId())
                .walkId(getWalk().getWalkId())
                .timeRecord(timeRecord)
                .build();
    }
}
