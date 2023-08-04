package kugods.wonder.app.walk.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kugods.wonder.app.common.cache.CacheKey;
import kugods.wonder.app.walk.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static com.querydsl.core.types.dsl.MathExpressions.*;
import static kugods.wonder.app.walk.entity.QIntermediateLocation.intermediateLocation;
import static kugods.wonder.app.walk.entity.QTag.tag;
import static kugods.wonder.app.walk.entity.QWalk.walk;

@Repository
@RequiredArgsConstructor
public class WalkCustomRepositoryImpl implements WalkCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Cacheable(value = CacheKey.ZONE, key = "{#request, #location}")
    @Override
    @Transactional(readOnly = true)
    public List<WalkResponse> getWalks(WalkListRequest request, UserLocation location) {
        return jpaQueryFactory
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
    }

    @Cacheable(value = CacheKey.ZONE, key = "{#walkId, #location}")
    @Override
    @Transactional(readOnly = true)
    public WalkResponse getWalkInfo(Long walkId, UserLocation location) {
        return jpaQueryFactory
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
    }

    @Cacheable(value = CacheKey.ZONE)
    @Override
    @Transactional(readOnly = true)
    public Map<Long, List<TagInfo>> getTagMap(List<WalkResponse> walks) {
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

    @Cacheable(value = CacheKey.ZONE, key = "#walkId.toString()")
    @Override
    @Transactional(readOnly = true)
    public List<TagInfo> getTagList(Long walkId) {
        return jpaQueryFactory
                .select(Projections.constructor(TagInfo.class,
                        tag.tagId,
                        tag.name
                ))
                .from(tag)
                .where(tag.walkTagMatches.any().walk.walkId.eq(walkId))
                .fetch();
    }

    @Cacheable(value = CacheKey.ZONE)
    @Override
    @Transactional(readOnly = true)
    public Map<Long, List<IntermediateLocationInfo>> getIntermediateLocationMap(List<WalkResponse> walks) {
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

    @Cacheable(value = CacheKey.ZONE, key = "#walkId.toString()")
    @Override
    @Transactional(readOnly = true)
    public List<IntermediateLocationInfo> getIntermediateLocationList(Long walkId) {
        return jpaQueryFactory
                .select(Projections.constructor(IntermediateLocationInfo.class,
                        intermediateLocation.intermediateLocationId,
                        intermediateLocation.latitude,
                        intermediateLocation.longitude
                ))
                .from(intermediateLocation)
                .where(intermediateLocation.walk.walkId.eq(walkId))
                .fetch();
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
}
