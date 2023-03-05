package kugods.wonder.app.record.service;

import kugods.wonder.app.record.dto.BookmarkDeleteRequest;
import kugods.wonder.app.record.dto.BookmarkRequest;
import kugods.wonder.app.record.dto.BookmarkResponse;
import kugods.wonder.app.record.entity.Bookmark;

import java.util.List;

public interface BookmarkService {
    List<BookmarkResponse> getBookmarks(Long memberId);

    BookmarkResponse addBookmark(BookmarkRequest request);

    BookmarkResponse deleteBookmark(BookmarkDeleteRequest request);
}
