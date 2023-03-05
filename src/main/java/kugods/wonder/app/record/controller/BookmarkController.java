package kugods.wonder.app.record.controller;

import kugods.wonder.app.common.dto.ApiDataResponse;
import kugods.wonder.app.record.dto.BookmarkRequest;
import kugods.wonder.app.record.dto.BookmarkResponse;
import kugods.wonder.app.record.service.BookmarkServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController {

    private final BookmarkServiceImpl bookmarkService;

    @GetMapping("/{memberId}")
    public ApiDataResponse<List<BookmarkResponse>> getBookmarkList(
            @PathVariable("memberId") Long memberId
    ) {
        return ApiDataResponse.of(bookmarkService.getBookmarks(memberId));
    }

    @PostMapping
    public ApiDataResponse<BookmarkResponse> addBookmark(
            @Validated @RequestBody BookmarkRequest request
    ) {
        return ApiDataResponse.of(bookmarkService.addBookmark(request));
    }

    @DeleteMapping("/delete/{bookmarkId}")
    public ApiDataResponse<BookmarkResponse> deleteBookmark(
            @PathVariable("bookmarkId") Long bookmarkId
    ) {
        return ApiDataResponse.of(bookmarkService.deleteBookmark(bookmarkId));
    }

}
