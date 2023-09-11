package kugods.wonder.app.common.lock.transaction;

import org.aspectj.lang.ProceedingJoinPoint;

public interface CallTransaction {
    Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable;
}
