package yevtukh.anton.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import yevtukh.anton.entities.Account;
import yevtukh.anton.entities.Bookmark;
import yevtukh.anton.exceptions.BookmarkNotFoundException;
import yevtukh.anton.exceptions.UserNotFoundException;
import yevtukh.anton.repositories.AccountRepository;
import yevtukh.anton.repositories.BookmarkRepository;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {

    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    Collection<Bookmark> readBookmarks(@PathVariable String userId) {
        validateUser(userId);
        return this.bookmarkRepository.findByAccountUsername(userId);
    }

    @PostMapping
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark bookmark) {
        validateUser(userId);
        return this.accountRepository.findByUsername(userId).map((Account account) -> {
            Bookmark result = bookmarkRepository.save(new Bookmark(account, bookmark.getUri(), bookmark.getDescription()));
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}").buildAndExpand(result.getUri()).toUri();
            return ResponseEntity.created(location).build();
        }).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{bookmarkId}")
    Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        this.validateUser(userId);
        return this.bookmarkRepository.findById(bookmarkId).orElseThrow(() -> new BookmarkNotFoundException(bookmarkId));
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
