package kugods.wonder.app.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CacheKey {
    LONG("long", 60 * 60),
    SHORT("short", 60 * 1),
    MEDIUM("medium", 60 * 5);

    private final String key;
    private final int expiryTimeSec;

    public String getKey() {
        return key;
    }

    public int getExpiryTimeSec() {
        return expiryTimeSec;
    }
}
