package kugods.wonder.app.common.lock.transaction;

import kugods.wonder.app.common.lock.transaction.CallTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedissonCallSameTransaction implements CallTransaction {
    @Transactional(propagation = Propagation.MANDATORY, timeout = 9)
    public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
