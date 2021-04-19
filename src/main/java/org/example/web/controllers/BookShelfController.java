package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        if ((book.getTitle().length() > 0) || (book.getAuthor().length() > 0) || (book.getSize() != null && book.getSize() > 0)) {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(Book book) {
        if (book.getId() != null) {
            bookService.removeBookById(book.getId().intValue());
        } else {
            if ((book.getAuthor().length() > 0) || (book.getTitle().length() > 0) || (book.getSize() != null && book.getSize() > 0)){
                String strAuthor = ".*";
                if (book.getAuthor().length() > 0) {
                    strAuthor = book.getAuthor() + strAuthor;
                }
                String strTitle = "";
                if (book.getTitle().length() > 0) {
                    strTitle = book.getTitle() + strTitle;
                }
                String strSize = "";
                if (book.getSize() != null && book.getSize() > 0) {
                    strSize = book.getSize().toString();
                }
                String str = strAuthor + strTitle + strSize;
                bookService.removeBooKByString(str);
            }
        }
        return "redirect:/books/shelf";
    }

    @GetMapping("/filter")
    public String filterBooks(Model model, Book book) {
        String str = "";
        if ((book.getAuthor().length() > 0) || (book.getTitle().length() > 0) || (book.getSize() != null && book.getSize() > 0)){
            String strAuthor = ".*";
            if (book.getAuthor().length() > 0) {
                strAuthor = book.getAuthor() + strAuthor;
            }
            String strTitle = "";
            if (book.getTitle().length() > 0) {
                strTitle = book.getTitle() + strTitle;
            }
            String strSize = "";
            if (book.getSize() != null && book.getSize() > 0) {
                strSize = book.getSize().toString();
            }
            str = strAuthor + strTitle + strSize;
        }
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getFilterBooks(str));
        return "book_shelf";
    }
}
