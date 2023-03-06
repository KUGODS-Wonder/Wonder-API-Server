package kugods.wonder.app.record.service;

import kugods.wonder.app.record.dto.BookmarkRequest;
import kugods.wonder.app.record.dto.BookmarkResponse;

import java.util.List;

public interface BookmarkService {
    List<BookmarkResponse> getBookmarks(String email);

    BookmarkResponse addBookmark(BookmarkRequest request);

    BookmarkResponse deleteBookmark(Long bookmarkId);
}
