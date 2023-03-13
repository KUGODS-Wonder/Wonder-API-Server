package kugods.wonder.app.walk.service;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kugods.wonder.app.walk.dto.UserLocation;
import kugods.wonder.app.walk.dto.WalkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.Expressions.numberTemplate;
import static com.querydsl.core.types.dsl.MathExpressions.*;
import static kugods.wonder.app.walk.entity.QWalk.walk;

@Service
@RequiredArgsConstructor
public class WalkServiceImpl implements WalkService {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    @Transactional(readOnly = true)
    public List<WalkResponse> getWalkList(UserLocation userLocation) {
        List<Tuple> results = jpaQueryFactory
                .select(
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
                )
                .from(walk)
                .orderBy(new OrderSpecifier<>(Order.ASC, Expressions.numberPath(Double.class, "boundary")))
                .fetch();

        return results.stream()
                .map(tuple -> WalkResponse.builder()
                        .walkId(tuple.get(walk.walkId))
                        .title(tuple.get(walk.title))
                        .distance(tuple.get(walk.distance))
                        .requiredTime(tuple.get(walk.requiredTime))
                        .theme(tuple.get(walk.theme))
                        .originLatitude(tuple.get(walk.originLatitude))
                        .originLongitude(tuple.get(walk.originLongitude))
                        .destinationLatitude(tuple.get(walk.destinationLatitude))
                        .destinationLongitude(tuple.get(walk.destinationLongitude))
                        .point(tuple.get(walk.point))
                        .boundary(tuple.get(10, Double.class))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WalkResponse getWalk(Long walkId, UserLocation userLocation) {
        Tuple findWalk =  jpaQueryFactory.
                select(
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
                )
                .from(walk)
                .where(walk.walkId.eq(walkId))
                .fetchOne();

        return WalkResponse.builder()
                .walkId(findWalk.get(walk.walkId))
                .title(findWalk.get(walk.title))
                .distance(findWalk.get(walk.distance))
                .requiredTime(findWalk.get(walk.requiredTime))
                .theme(findWalk.get(walk.theme))
                .originLatitude(findWalk.get(walk.originLatitude))
                .originLongitude(findWalk.get(walk.originLongitude))
                .destinationLatitude(findWalk.get(walk.destinationLatitude))
                .destinationLongitude(findWalk.get(walk.destinationLongitude))
                .point(findWalk.get(walk.point))
                .boundary(findWalk.get(10, Double.class))
                .build();
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
}
