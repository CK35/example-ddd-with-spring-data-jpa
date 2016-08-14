package de.ck35.example.ddd.jpa.bookstore;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.ck35.example.ddd.domain.bookstore.Book;
import de.ck35.example.ddd.domain.bookstore.Bookshelf;
import de.ck35.example.ddd.domain.bookstore.Bookstore;
import de.ck35.example.ddd.jpa.bookstore.BookEntity.BookRepository;
import de.ck35.example.ddd.jpa.bookstore.BookshelfEntity.BookshelfRepository;

@Service
public class BookstoreService implements Bookstore {

    @Autowired BookshelfRepository bookshelfRepository;
    @Autowired BookRepository bookRepository;
    
    @Override
    public Book register(String title, double price) {
        BookEntity book = new BookEntity();
        book.setTitle(title);
        book.setPrice(price);
        return bookRepository.save(book);
    }
    
    @Override
    public Bookshelf addBookshelf(String category) {
        BookshelfEntity entity = new BookshelfEntity();
        entity.setCategory(category);
        return bookshelfRepository.save(entity);
    }

    @Override
    public Optional<Bookshelf> findShelf(String category) {
        return Optional.ofNullable(bookshelfRepository.findByCategory(category));
    }

}