package kugods.wonder.app.common.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static kugods.wonder.app.common.cache.TTLKey.*;

@RequiredArgsConstructor
public enum CacheKey {

    WALKS_BY_USER_LOCATION("walks_by_user_location", ONE_DAY.getExpiryTimeSec()),
    WALK_INFO_BY_USER_LOCATION("walk_info_by_user_location", ONE_DAY.getExpiryTimeSec()),
    TAG_MAP_OF_WALK_LIST("tag_map_of_walk_list", ONE_DAY.getExpiryTimeSec()),
    TAG_INFO_OF_WALK("tag_info_of_walk", ONE_MONTH.getExpiryTimeSec()),
    INTERMEDIATE_LOCATION_OF_WALK("intermediate_location_of_walk", ONE_MONTH.getExpiryTimeSec()),
    MY_RESERVATIONS("my_reservations", ONE_MIN.getExpiryTimeSec())
    ;

    private final String key;
    private final int expiryTimeSec;

    public String getKey() {
        return key;
    }

    public int getExpiryTimeSec() {
        return expiryTimeSec;
    }
}
