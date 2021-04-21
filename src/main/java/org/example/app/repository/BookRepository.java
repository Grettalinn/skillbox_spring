package org.example.app.repository;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public void removeItemById(Integer bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
    }

    @Override
    public void removeItemByString(String bookStrToRemove) {
        Pattern patternAuthor = Pattern.compile(bookStrToRemove, Pattern.CASE_INSENSITIVE);
        for (Book book : retreiveAll()) {
            String bookSize = "";
            if (book.getSize() != null) {
                bookSize = book.getSize().toString();
            }
            String text = book.getAuthor() +" "+ book.getTitle() + " " + bookSize;
            logger.info("compare Author " + bookStrToRemove + " with " + text);
            Matcher match = patternAuthor.matcher(text);
            if (match.find()) {
                logger.info("find " + text.substring(match.start(), match.end()));
                repo.remove(book);
            }
        }
    }

    @Override
    public List<Book> filterItems(String bookParams) {
        List<Book> filterBooks = new ArrayList<>();
        if (bookParams.length() > 0) {
            Pattern patternAuthor = Pattern.compile(bookParams, Pattern.CASE_INSENSITIVE);
            for (Book book : retreiveAll()) {
                String bookSize = "";
                if (book.getSize() != null) {
                    bookSize = book.getSize().toString();
                }
                String text = book.getAuthor() + " " + book.getTitle() + " " + bookSize;
                logger.info("find " + bookParams + " with " + text);
                Matcher match = patternAuthor.matcher(text);
                if ((match.find())) {
                    logger.info("filter " + text.substring(match.start(), match.end()));
                    filterBooks.add(book);
                }
            }
        } else {
            filterBooks = repo;
        }
        return filterBooks;
    }
}
