package kugods.wonder.app.walk.service;

import kugods.wonder.app.walk.dto.UserLocation;
import kugods.wonder.app.walk.dto.WalkResponse;

import java.util.List;

public interface WalkService {
    List<WalkResponse> getWalk(UserLocation userLocation);
}
