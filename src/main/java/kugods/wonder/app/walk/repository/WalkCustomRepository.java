package kugods.wonder.app.walk.repository;

import kugods.wonder.app.walk.dto.*;

import java.util.List;
import java.util.Map;

public interface WalkCustomRepository {
    List<WalkResponse> getWalks(WalkListRequest request, UserLocation location);

    WalkResponse getWalkInfo(Long walkId, UserLocation location);

    Map<Long, List<TagInfo>> getTagMap(List<WalkResponse> walks);

    List<TagInfo> getTagList(Long walkId);

    Map<Long, List<IntermediateLocationInfo>> getIntermediateLocationMap(List<WalkResponse> walks);

    List<IntermediateLocationInfo> getIntermediateLocationList(Long walkId);
}
