package kugods.wonder.app.walk.entity;

import kugods.wonder.app.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WalkTagMatch")
@Entity
public class WalkTagMatch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "walk_id")
    private Walk walk;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

}
