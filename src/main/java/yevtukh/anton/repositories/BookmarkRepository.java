package yevtukh.anton.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yevtukh.anton.entities.Bookmark;

import java.util.Collection;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
