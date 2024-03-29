package kugods.wonder.app.common.lock.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CallTransactionFactory {

    private final RedissonCallSameTransaction redissonCallSameTransaction;
    private final RedissonCallNewTransaction redissonCallNewTransaction;

    public CallTransaction getCallTransaction(boolean needSame) {
        if (needSame) {
            return redissonCallSameTransaction;
        }
        return redissonCallNewTransaction;
    }
}
