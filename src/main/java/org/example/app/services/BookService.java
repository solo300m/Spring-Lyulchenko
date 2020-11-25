package org.example.app.services;


import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks(){
        return bookRepo.retreiveAll();
    }

    public void filterBookOff(){
        bookRepo.filterOut();
    }

    public boolean saveBook(Book book) {
        return bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove){
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public int removeFilterBook(String author, String title, int size){
        return bookRepo.removeFilter(author,title,size);
    }

    public List<Book> getFilteredBooks(String author, String title, int size){
        return bookRepo.retreiveFilterAll(author, title, size);
    }

    public boolean filterBook(String author, String title, int size) {
        return bookRepo.filterItemBook(author,title,size);
    }
}
