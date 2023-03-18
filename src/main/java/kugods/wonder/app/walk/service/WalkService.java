package kugods.wonder.app.walk.service;

import kugods.wonder.app.walk.dto.UserLocation;
import kugods.wonder.app.walk.dto.WalkInfo;
import kugods.wonder.app.walk.dto.WalkResponse;

import java.util.List;

public interface WalkService {
    
    List<WalkInfo> getWalkList(UserLocation userLocation);
    
    WalkResponse getWalk(Long walkId, UserLocation userLocation);
}
