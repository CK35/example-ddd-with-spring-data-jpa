package de.ck35.example.ddd.jpa.bookstore;

import java.util.Optional;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import de.ck35.example.ddd.domain.bookstore.Book;
import de.ck35.example.ddd.domain.bookstore.Bookshelf;
import de.ck35.example.ddd.jpa.NumericSequenceId;
import de.ck35.example.ddd.jpa.SpringEntityListener;
import de.ck35.example.ddd.jpa.bookstore.BookEntity.BookRepository;
import de.ck35.example.ddd.jpa.bookstore.BookshelfSpaceEntity.BookshelfSpaceRepository;

@Entity
@Table(name="bookshelf")
@EntityListeners(SpringEntityListener.class)
public class BookshelfEntity implements Bookshelf {

    @EmbeddedId
    private NumericSequenceId id;
    
    private String category;
    
    @Autowired transient BookRepository bookRepository;
    @Autowired transient BookshelfSpaceRepository bookshelfSpaceRepository;
    
    public BookshelfEntity() {
        this.id = new NumericSequenceId();
    }
    @Override
    public NumericSequenceId getId() {
        return id;
    }
    public void setId(NumericSequenceId id) {
        this.id = id;
    }
    @Override
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
    @Override
    public Optional<Space> findBook(String title) {
        return Optional.ofNullable(bookRepository.findByTitle(title))
                       .map(book -> bookshelfSpaceRepository.findByBookshelfAndBook(this, book));
    }
    @Override
    public Space store(Book book, int quantity) {
        BookshelfSpaceEntity entity = bookshelfSpaceRepository.findByBookshelfAndBook(this, (BookEntity) book);
        if(entity == null) {
            entity = new BookshelfSpaceEntity();
            entity.setBook((BookEntity) book);
            entity.setBookshelf(this);
        }
        entity.setQuantity(entity.getQuantity() + quantity);
        return bookshelfSpaceRepository.save(entity);
    }
    
    public static interface BookshelfRepository extends CrudRepository<BookshelfEntity, NumericSequenceId> {
        
        BookshelfEntity findByCategory(String category);
        
    }
    
}