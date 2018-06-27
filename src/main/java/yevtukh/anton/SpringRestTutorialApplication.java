package yevtukh.anton;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import yevtukh.anton.entities.Account;
import yevtukh.anton.entities.Bookmark;
import yevtukh.anton.repositories.AccountRepository;
import yevtukh.anton.repositories.BookmarkRepository;

import java.util.Arrays;

@SpringBootApplication
public class SpringRestTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestTutorialApplication.class, args);
	}

	@Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           BookmarkRepository bookmarkRepository) {
		return (String[] args) -> Arrays.asList("jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                .forEach((String name) -> {
							Account account = accountRepository.save(new Account(name, "password"));
							bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + name,
                                    "A description"));
							bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + name,
                                    "A description"));
						});
	}
}
