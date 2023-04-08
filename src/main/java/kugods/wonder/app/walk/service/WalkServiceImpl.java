package kugods.wonder.app.walk.service;

import kugods.wonder.app.walk.dto.*;
import kugods.wonder.app.walk.exception.WalkDoesNotExistException;
import kugods.wonder.app.walk.repository.WalkCustomRepository;
import kugods.wonder.app.walk.repository.WalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WalkServiceImpl implements WalkService {

    private final WalkRepository walkRepository;
    private final WalkCustomRepository walkCustomRepository;

    @Override
    public List<WalkResponse> getWalkList(WalkListRequest request) {
        UserLocation location = UserLocation.to(request.getLatitude(), request.getLongitude());
        List<WalkResponse> walks = walkCustomRepository.getWalks(request, location);

        Map<Long, List<IntermediateLocationInfo>> intermediateLocationMap = walkCustomRepository.getIntermediateLocationMap(walks);
        Map<Long, List<TagInfo>> tagMap = walkCustomRepository.getTagMap(walks);

        walks.forEach(walkInfo -> {
            walkInfo.setIntermediateLocationList(intermediateLocationMap
                    .getOrDefault(walkInfo.getWalkId(), Collections.emptyList()));
            walkInfo.setTagList(tagMap
                    .getOrDefault(walkInfo.getWalkId(), Collections.emptyList()));
        });
        return walks;
    }

    @Override
    public WalkResponse getWalk(Long walkId, UserLocation location) {
        validateWalkExists(walkId);
        WalkResponse walkInfo = walkCustomRepository.getWalkInfo(walkId, location);

        List<IntermediateLocationInfo> intermediateLocationList = walkCustomRepository.getIntermediateLocationList(walkId);
        List<TagInfo> tagList = walkCustomRepository.getTagList(walkId);

        walkInfo.setIntermediateLocationList(intermediateLocationList);
        walkInfo.setTagList(tagList);

        return walkInfo;
    }

    private void validateWalkExists(Long walkId) {
        if (walkRepository.findById(walkId).isEmpty()) {
            throw new WalkDoesNotExistException();
        }
    }
}
