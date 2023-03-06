package kugods.wonder.app.record.entity;

import kugods.wonder.app.common.entity.BaseEntity;
import kugods.wonder.app.member.entity.Member;
import kugods.wonder.app.record.dto.BookmarkResponse;
import kugods.wonder.app.walk.entity.Walk;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Bookmark")
@Entity
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    @Column(length = 45, nullable = false)
    private String title;

    @Column(length = 200, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_id")
    private Walk walk;

    public BookmarkResponse toReponse(){
        return BookmarkResponse.builder()
                .bookmarkId(getBookmarkId())
                .title(title)
                .contents(content)
                .build();
    }
}
