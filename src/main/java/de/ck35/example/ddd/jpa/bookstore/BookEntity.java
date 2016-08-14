package de.ck35.example.ddd.jpa.bookstore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.repository.CrudRepository;

import de.ck35.example.ddd.domain.bookstore.Book;
import de.ck35.example.ddd.jpa.NumericSequenceId;

@Entity
@Table(name="book")
public class BookEntity implements Book {

    @EmbeddedId
    private NumericSequenceId id;
    
    private String title;
    private double price;
    
    public BookEntity() {
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
    public static interface BookRepository extends CrudRepository<BookEntity, NumericSequenceId> {
        
        BookEntity findByTitle(String title);
        
    }
}