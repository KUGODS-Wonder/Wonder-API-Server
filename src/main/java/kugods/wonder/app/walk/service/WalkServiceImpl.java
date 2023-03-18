package kugods.wonder.app.walk.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kugods.wonder.app.walk.dto.*;
import kugods.wonder.app.walk.exception.WalkDoesNotExistException;
import kugods.wonder.app.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static com.querydsl.core.types.dsl.MathExpressions.*;
import static kugods.wonder.app.walk.entity.QWalk.walk;
import static kugods.wonder.app.walk.entity.QIntermediateLocation.intermediateLocation;
import static kugods.wonder.app.walk.entity.QWalkTagMatch.walkTagMatch;
import static kugods.wonder.app.walk.entity.QTag.tag;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalkServiceImpl implements WalkService {

    private final JPAQueryFactory jpaQueryFactory;

    private final WalkRepository walkRepository;


    @Override
    @Transactional(readOnly = true)
    public List<WalkInfo> getWalkList(UserLocation userLocation) {
//        List<WalkInfo> walks = jpaQueryFactory
//                .select(Projections.constructor(WalkInfo.class,
//                        walk.walkId,
//                        walk.title,
//                        walk.distance,
//                        walk.requiredTime,
//                        walk.theme,
//                        walk.originLatitude,
//                        walk.originLongitude,
//                        walk.destinationLatitude,
//                        walk.destinationLongitude,
//                        walk.point,
//                        calculateDistanceBetweenTwoPoint(userLocation).as("boundary")
//                ))
//                .from(walk)
//                .orderBy(Expressions.numberPath(Double.class, "boundary").asc())
//                .fetch();
//
//        return walks.stream()
//                .map(walkInfo -> WalkResponse.builder()
//                        .walkId(walkInfo.getWalkId())
//                        .title(walkInfo.getTitle())
//                        .distance(walkInfo.getDistance())
//                        .requiredTime(walkInfo.getRequiredTime())
//                        .theme(walkInfo.getTheme())
//                        .originLatitude(walkInfo.getOriginLatitude())
//                        .originLongitude(walkInfo.getOriginLongitude())
//                        .destinationLatitude(walkInfo.getDestinationLatitude())
//                        .destinationLongitude(walkInfo.getDestinationLongitude())
//                        .point(walkInfo.getPoint())
//                        .boundary(walkInfo.getBoundary())
//                        .intermediateLocations(getIntermediateLocations(walkInfo.getWalkId()))
//                        .tagList(getTagList(walkInfo.getWalkId()))
//                        .build())
//                .collect(Collectors.toList());
        List<WalkInfo> walks = jpaQueryFactory
                .select(Projections.constructor(WalkInfo.class,
                        walk.walkId,
                        walk.title,
                        walk.distance,
                        walk.requiredTime,
                        walk.theme,
                        walk.originLatitude,
                        walk.originLongitude,
                        walk.destinationLatitude,
                        walk.destinationLongitude,
                        walk.point,
                        calculateDistanceBetweenTwoPoint(userLocation).as("boundary")
                ))
                .from(walk)
                .leftJoin(walk.intermediateLocations, intermediateLocation)
                .leftJoin(walk.walkTagMatches, walkTagMatch)
                .leftJoin(walkTagMatch.tag, tag)
                .orderBy(Expressions.numberPath(Double.class, "boundary").asc())
                .fetch();

        Map<Long, List<IntermediateLocationInfo>> intermediateLocationMap = jpaQueryFactory
                .select(Projections.constructor(IntermediateLocationInfo.class,
                        intermediateLocation.intermediateLocationId,
                        intermediateLocation.latitude,
                        intermediateLocation.longitude
                ))
                .from(intermediateLocation)
                .where(intermediateLocation.walk.walkId.in(walks.stream().map(WalkInfo::getWalkId).collect(Collectors.toList())))
                .transform(GroupBy.groupBy(intermediateLocation.walk.walkId).as(GroupBy.list(
                        Projections.constructor(IntermediateLocationInfo.class,
                                intermediateLocation.intermediateLocationId,
                                intermediateLocation.latitude,
                                intermediateLocation.longitude
                        )
                )));

        Map<Long, List<TagInfo>> tagMap = jpaQueryFactory
                .select(Projections.constructor(TagInfo.class,
                        tag.tagId,
                        tag.name
                ))
                .from(tag)
                .where(tag.walkTagMatches.any().walk.walkId.in(walks.stream().map(WalkInfo::getWalkId).collect(Collectors.toList())))
                .transform(GroupBy.groupBy(tag.walkTagMatches.any().walk.walkId).as(GroupBy.list(
                        Projections.constructor(TagInfo.class,
                                tag.tagId,
                                tag.name
                        )
                )));

        for (WalkInfo walkInfo : walks) {
            walkInfo.setIntermediateLocationList(intermediateLocationMap.getOrDefault(walkInfo.getWalkId(), Collections.emptyList()));
            walkInfo.setTagList(tagMap.getOrDefault(walkInfo.getWalkId(), Collections.emptyList()));
        }

        return walks;
    }

    @Override
    @Transactional(readOnly = true)
    public WalkResponse getWalk(Long walkId, UserLocation userLocation) {
        validateWalkExists(walkId);
        return jpaQueryFactory
                .select(Projections.constructor(WalkResponse.class,
                        walk.walkId,
                        walk.title,
                        walk.distance,
                        walk.requiredTime,
                        walk.theme,
                        walk.originLatitude,
                        walk.originLongitude,
                        walk.destinationLatitude,
                        walk.destinationLongitude,
                        walk.point,
                        calculateDistanceBetweenTwoPoint(userLocation).as("boundary"),
                        intermediateLocation,
                        tag
                ))
                .from(walk)
                .leftJoin(walk.intermediateLocations, intermediateLocation)
                .leftJoin(walk.walkTagMatches, walkTagMatch)
                .leftJoin(walkTagMatch.tag, tag)
                .fetchJoin()
                .distinct()
                .where(walk.walkId.eq(walkId))
                .fetchOne();
    }

    private NumberExpression<Double> calculateDistanceBetweenTwoPoint(UserLocation userLocation) {
        return numberTemplate(Double.class, "6317.0").multiply(
                acos(
                        cos(radians(walk.originLatitude))
                                .multiply(cos(radians(numberTemplate(BigDecimal.class, userLocation.getLatitude().toString()))))
                                .multiply(cos(radians(numberTemplate(BigDecimal.class, userLocation.getLongitude().toString()))
                                        .subtract(radians(walk.originLongitude)))
                                )
                                .add(sin(radians(walk.originLatitude))
                                        .multiply(sin(radians(numberTemplate(BigDecimal.class, userLocation.getLatitude().toString())))))
                ));
    }

    private List<IntermediateLocationInfo> getIntermediateLocations(Long walkId) {
        return jpaQueryFactory
                .select(Projections.constructor(IntermediateLocationInfo.class,
                        intermediateLocation.intermediateLocationId,
                        intermediateLocation.latitude,
                        intermediateLocation.longitude))
                .from(walk)
                .join(walk.intermediateLocations, intermediateLocation)
                .where(walk.walkId.eq(walkId))
                .fetch();
    }

    private List<TagInfo> getTagList(Long walkId) {
        return jpaQueryFactory
                .select(Projections.constructor(TagInfo.class,
                        tag.tagId,
                        tag.name))
                .from(walk)
                .join(walk.walkTagMatches, walkTagMatch)
                .join(walkTagMatch.tag, tag)
                .where(walk.walkId.eq(walkId))
                .fetch();
    }


    private void validateWalkExists(Long walkId) {
        if (walkRepository.findById(walkId).isEmpty()) {
            throw new WalkDoesNotExistException();
        }
    }
}
