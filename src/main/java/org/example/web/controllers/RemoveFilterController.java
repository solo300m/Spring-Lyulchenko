package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.slf4j.event.EventRecodingLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "remove_filter")
public class RemoveFilterController {
    private final Logger logger = Logger.getLogger(RemoveFilterController.class);
    private BookService bookService;

    @Autowired
    public RemoveFilterController(BookService bookService){
        logger.info("wow");
        this.bookService = bookService;
    }

    @GetMapping//("/data")
    public String remove_filter(Model model){
        logger.info("Interest page Wow!!!");
        model.addAttribute("book",new Book());
        model.addAttribute("bookList",bookService.getAllBooks());
        return "group_remove_book";
    }
    @PostMapping("/data")
    public String dataRemoveFilter(String author, String title, Integer size){
        String aut;
        String tit;
        int siz = 0;
        if(author == null)
            aut = "";
        else
            aut = author;
        if(title == null)
            tit = "";
        else
            tit = title;
        if(size == null)
            siz = 0;
        else
            siz = size;
        logger.info("remove to filter "+ aut +", "+ tit +", "+siz);
        if(aut.equals("") && tit.equals("") && siz == 0)
            return"redirect:/remove_filter";
        if(bookService.removeFilterBook(aut,tit,siz) != 0)
            return"redirect:/books/shelf";
        else
            return"redirect:/remove_filter";
    }

    @GetMapping("/books")
    public String cancelFilter(){
        return"redirect:/books/shelf";
    }
}
