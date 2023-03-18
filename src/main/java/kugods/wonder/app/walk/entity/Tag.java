package kugods.wonder.app.walk.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tag")
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(length = 45, nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<WalkTagMatch> walkTagMatches = new ArrayList<>();

}
