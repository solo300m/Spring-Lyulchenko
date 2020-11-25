package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private List<Book> repoFiltered = new ArrayList<>();

    @Override
    public boolean store(Book book) {
        if (!book.getAutor().trim().isEmpty() && !book.getTitle().trim().isEmpty() && book.getSize()!=0) {
            book.setId(book.hashCode());
            logger.info("store new book: " + book);
            repo.add(book);
            return true;
        }
        else {
            logger.info("no book add ");
            return false;
        }
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        if(!bookIdToRemove.equals(null)) {
            for (Book book : retreiveAll()) {
                if (book.getId().equals(bookIdToRemove)) {
                    logger.info("remove book completed: " + book);
                    return repo.remove(book);
                }
            }
            return false;
        }
        else
            return false;
    }

    @Override
    public int removeFilter(String author, String title, int size) {
        int count = 0;
        if(!author.equals("") && !title.equals("") && size!=0){
            for(Book book: retreiveAll()){
                if(book.getAutor().equals(author) && book.getTitle().equals(title) && book.getSize()==size){
                    logger.info("remove filtered book completed: "+book);
                    repo.remove(book);
                    count++;
                }
            }
            return count;
        }
        else if(!author.equals("") && !title.equals("") && size==0) {
            count = 0;
            for (Book book : retreiveAll()) {
                if (book.getAutor().equals(author) && book.getTitle().equals(title)) {
                    logger.info("remove filtered book completed: " + book);
                    repo.remove(book);
                    count++;
                }
            }
            return count;
        }
        else if(!author.equals("") && title.equals("") && size==0) {
            count = 0;
            for (Book book : retreiveAll()) {
                if (book.getAutor().equals(author)) {
                    logger.info("remove filtered book completed: " + book);
                    repo.remove(book);
                    count++;
                }
            }
            return count;
        }
        else if(author.equals("") && !title.equals("") && size==0) {
            count = 0;
            for (Book book : retreiveAll()) {
                if (book.getTitle().equals(title)) {
                    logger.info("remove filtered book completed: " + book);
                    repo.remove(book);
                    count++;
                }
            }
            return count;
        }
        else if(author.equals("") && title.equals("") && size!=0) {
            count = 0;
            for (Book book : retreiveAll()) {
                if (book.getSize() == size) {
                    logger.info("remove filtered book completed: " + book);
                    repo.remove(book);
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

    @Override
    public void filterOut() {
        repoFiltered.clear();
    }

    @Override
    public List<Book> retreiveAll() {
        if(repoFiltered.size()!=0){
            return new ArrayList<>(repoFiltered);
        }
        else
            return new ArrayList<>(repo);
    }
    @Override

    //retreiveAll().stream().filter(w -> author.isEmpty() || w.getAutor().equals(author))
    // .filter(w -> title.isEmpty() || w.getTitle().equals(title))
    //        .filter(w -> size == 0 || w.getSize().equals(size))
    //        .collect(Collectors.toList());
    public List<Book> retreiveFilterAll(String author, String title, int size) {
        //List<Book> listReturn = new ArrayList<>();
        if(!author.equals("") && !title.equals("") && size!=0) {
            Stream<Book> filter = retreiveAll().stream().filter(w -> w.getAutor().equals(author) && w.getTitle().equals(title) && w.getSize() == size);
            repoFiltered = filter.collect(Collectors.toList());
        }
        else if(author.equals("") && title.equals("") && size == 0){
            Stream<Book> filter = retreiveAll().stream()/*.filter(w-> w.getAutor().isEmpty() && w.getTitle().isEmpty() && w.getSize() == 0)*/;
            repoFiltered = filter.collect(Collectors.toList());
        }
        else if(!author.equals("") && title.equals("") && size==0){
            Stream<Book>filter = retreiveAll().stream().filter(w -> w.getAutor().equals(author));
            repoFiltered = filter.collect(Collectors.toList());
        }
        else if(author.equals("") && !title.equals("") && size==0){
            Stream<Book>filter = retreiveAll().stream().filter(w -> w.getTitle().equals(title));
            repoFiltered = filter.collect(Collectors.toList());
        }
        else if(author.equals("") && title.equals("") && size!=0){
            Stream<Book>filter = retreiveAll().stream().filter(w -> w.getSize() == size);
            repoFiltered = filter.collect(Collectors.toList());
        }
        else if(!author.equals("") && !title.equals("") && size==0) {
            Stream<Book>filter = retreiveAll().stream().filter(w -> w.getAutor().equals(author) && w.getTitle().equals(title));
            repoFiltered = filter.collect(Collectors.toList());
        }
        else if(!author.equals("") && title.equals("") && size!=0) {
            Stream<Book>filter = retreiveAll().stream().filter(w -> w.getAutor().equals(author) && w.getSize() == size);
            repoFiltered = filter.collect(Collectors.toList());
        }
        else if(author.equals("") && !title.equals("") && size!=0) {
            Stream<Book>filter = retreiveAll().stream().filter(w -> w.getTitle().equals(title) && w.getSize() == size);
            repoFiltered = filter.collect(Collectors.toList());
        }
        return repoFiltered;
    }

    @Override
    public boolean filterItemBook(String author, String title, int size) {
        if(retreiveFilterAll(author,title,size).size()!=retreiveAll().size())
            return true;
        else
            return false;
    }
}
