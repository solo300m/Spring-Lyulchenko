package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String books(Model model){
        logger.info("got book shelf general");
        model.addAttribute("book",new Book());
        model.addAttribute("bookList",bookService.getAllBooks());
        return "book_shelf";
    }
    @GetMapping("/filter_out")
    public String newbooks(Model model){
        logger.info("got book shelf no filtered");
        bookService.filterBookOff();
        model.addAttribute("book",new Book());
        model.addAttribute("bookList",bookService.getAllBooks());
        return "book_shelf";
    }
    @PostMapping("/save")
    public String saveBook(Book book){
        if(bookService.saveBook(book)) {
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
        else
            return"redirect:/books/shelf";
    }

    @PostMapping("/remove")//@RequestParam(value = "bookIdToRemove")
    public String removeBook(Integer bookIdToRemove){
        logger.info("bookIdRemove is - "+bookIdToRemove);
        if(bookIdToRemove == null)
            return "redirect:/books/shelf";
        if(bookService.removeBookById(bookIdToRemove)){
            return "redirect:/books/shelf";
        }
        else{
            return "redirect:/books/shelf";//"book_shelf";
        }
    }

    @GetMapping("/remove_filter")
    public String rFilter(Model model){
        logger.info("Remove page");
        model.addAttribute("book",new Book());
        model.addAttribute("bookList",bookService.getAllBooks());
        return "group_remove_book";
    }

    @GetMapping("/filter")
    public String vFilter(Model model){
        logger.info("Filter page");
        model.addAttribute("book",new Book());
        model.addAttribute("bookList",bookService.getAllBooks());
        return "filter_book";
    }

}
