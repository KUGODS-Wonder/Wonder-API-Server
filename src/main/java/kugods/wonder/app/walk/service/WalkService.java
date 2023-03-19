package kugods.wonder.app.walk.service;

import kugods.wonder.app.walk.dto.UserLocation;
import kugods.wonder.app.walk.dto.WalkResponse;
import kugods.wonder.app.walk.dto.WalkListRequest;

import java.util.List;

public interface WalkService {
    
    List<WalkResponse> getWalkList(WalkListRequest walkListRequest);
    
    WalkResponse getWalk(Long walkId, UserLocation userLocation);
}
