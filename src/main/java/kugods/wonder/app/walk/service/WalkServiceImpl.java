package kugods.wonder.app.walk.service;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kugods.wonder.app.walk.dto.*;
import kugods.wonder.app.walk.exception.WalkDoesNotExistException;
import kugods.wonder.app.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static com.querydsl.core.types.dsl.MathExpressions.*;
import static kugods.wonder.app.walk.entity.QIntermediateLocation.intermediateLocation;
import static kugods.wonder.app.walk.entity.QTag.tag;
import static kugods.wonder.app.walk.entity.QWalk.walk;

@Service
@RequiredArgsConstructor
public class WalkServiceImpl implements WalkService {

    private final JPAQueryFactory jpaQueryFactory;
    private final WalkRepository walkRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WalkResponse> getWalkList(WalkListRequest request) {
        UserLocation location = UserLocation.to(request.getLatitude(), request.getLongitude());
        List<WalkResponse> walks = jpaQueryFactory
                .select(Projections.constructor(WalkResponse.class,
                        walk.walkId,
                        walk.title,
                        walk.pathDistance,
                        walk.requiredTime,
                        walk.address,
                        walk.theme,
                        walk.originLatitude,
                        walk.originLongitude,
                        walk.destinationLatitude,
                        walk.destinationLongitude,
                        walk.point,
                        calculateDistanceBetweenTwoPoint(location).as("distanceToUser")
                ))
                .from(walk)
                .where(calculateDistanceBetweenTwoPoint(location).loe(request.getRange()))
                .orderBy(Expressions.numberPath(Double.class, "distanceToUser").asc())
                .fetch();

        Map<Long, List<IntermediateLocationInfo>> intermediateLocationMap = getIntermediateLocationMap(walks);
        Map<Long, List<TagInfo>> tagMap = getTagMap(walks);

        walks.forEach(walkInfo -> {
            walkInfo.setIntermediateLocationList(intermediateLocationMap
                    .getOrDefault(walkInfo.getWalkId(), Collections.emptyList()));
            walkInfo.setTagList(tagMap
                    .getOrDefault(walkInfo.getWalkId(), Collections.emptyList()));
        });
        return walks;
    }

    @Override
    @Transactional(readOnly = true)
    public WalkResponse getWalk(Long walkId, UserLocation location) {
        validateWalkExists(walkId);
        WalkResponse walkInfo = jpaQueryFactory
                .select(Projections.constructor(WalkResponse.class,
                        walk.walkId,
                        walk.title,
                        walk.pathDistance,
                        walk.requiredTime,
                        walk.address,
                        walk.theme,
                        walk.originLatitude,
                        walk.originLongitude,
                        walk.destinationLatitude,
                        walk.destinationLongitude,
                        walk.point,
                        calculateDistanceBetweenTwoPoint(location).as("distanceToUser")
                ))
                .from(walk)
                .where(walk.walkId.eq(walkId))
                .fetchOne();

        List<IntermediateLocationInfo> intermediateLocationList = getIntermediateLocationList(walkId);
        List<TagInfo> tagList = getTagList(walkId);

        walkInfo.setIntermediateLocationList(intermediateLocationList);
        walkInfo.setTagList(tagList);

        return walkInfo;
    }

    private Map<Long, List<TagInfo>> getTagMap(List<WalkResponse> walks) {
        return jpaQueryFactory
                .select(Projections.constructor(TagInfo.class,
                                tag.tagId,
                                tag.name
                        )
                )
                .from(tag)
                .where(tag.walkTagMatches.any().walk.walkId.in(
                                walks.stream()
                                        .map(WalkResponse::getWalkId)
                                        .collect(Collectors.toList())
                        )
                )
                .transform(GroupBy.groupBy(tag.walkTagMatches.any().walk.walkId)
                        .as(GroupBy.list(
                                        Projections.constructor(TagInfo.class,
                                                tag.tagId,
                                                tag.name
                                        )
                                )
                        )
                );
    }

    private List<TagInfo> getTagList(Long walkId) {
        List<TagInfo> tagList = jpaQueryFactory
                .select(Projections.constructor(TagInfo.class,
                        tag.tagId,
                        tag.name
                ))
                .from(tag)
                .where(tag.walkTagMatches.any().walk.walkId.eq(walkId))
                .fetch();
        return tagList;
    }

    private Map<Long, List<IntermediateLocationInfo>> getIntermediateLocationMap(List<WalkResponse> walks) {
        return jpaQueryFactory
                .select(Projections.constructor(IntermediateLocationInfo.class,
                                intermediateLocation.intermediateLocationId,
                                intermediateLocation.latitude,
                                intermediateLocation.longitude
                        )
                )
                .from(intermediateLocation)
                .where(intermediateLocation.walk.walkId.in(
                                walks.stream()
                                        .map(WalkResponse::getWalkId)
                                        .collect(Collectors.toList())
                        )
                )
                .transform(GroupBy.groupBy(intermediateLocation.walk.walkId)
                        .as(GroupBy.list(
                                        Projections.constructor(IntermediateLocationInfo.class,
                                                intermediateLocation.intermediateLocationId,
                                                intermediateLocation.latitude,
                                                intermediateLocation.longitude
                                        )
                                )
                        )
                );
    }

    private List<IntermediateLocationInfo> getIntermediateLocationList(Long walkId) {
        List<IntermediateLocationInfo> intermediateLocationList = jpaQueryFactory
                .select(Projections.constructor(IntermediateLocationInfo.class,
                        intermediateLocation.intermediateLocationId,
                        intermediateLocation.latitude,
                        intermediateLocation.longitude
                ))
                .from(intermediateLocation)
                .where(intermediateLocation.walk.walkId.eq(walkId))
                .fetch();
        return intermediateLocationList;
    }

    private NumberExpression<Double> calculateDistanceBetweenTwoPoint(UserLocation location) {
        return numberTemplate(Double.class, "6317.0").multiply(
                acos(
                        cos(radians(walk.originLatitude))
                                .multiply(cos(radians(numberTemplate(BigDecimal.class, location.getLatitude().toString()))))
                                .multiply(cos(radians(numberTemplate(BigDecimal.class, location.getLongitude().toString()))
                                        .subtract(radians(walk.originLongitude)))
                                )
                                .add(sin(radians(walk.originLatitude))
                                        .multiply(sin(radians(numberTemplate(BigDecimal.class, location.getLatitude().toString())))))
                ));
    }

    private void validateWalkExists(Long walkId) {
        if (walkRepository.findById(walkId).isEmpty()) {
            throw new WalkDoesNotExistException();
        }
    }
}
