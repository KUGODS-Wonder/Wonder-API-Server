package kugods.wonder.app.walk.controller;

import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.walk.dto.UserLocation;
import kugods.wonder.app.walk.dto.WalkResponse;
import kugods.wonder.app.walk.service.WalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/walk")
public class WalkController {

    private final WalkService walkService;

    @GetMapping
    public ApiDataResponse<List<WalkResponse>> getWalkList(
            @Validated @RequestBody UserLocation userLocation
    ) {
        return ApiDataResponse.of(walkService.getWalk(userLocation));
    }
}
