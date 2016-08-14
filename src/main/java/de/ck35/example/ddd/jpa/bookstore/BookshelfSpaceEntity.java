package de.ck35.example.ddd.jpa.bookstore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import de.ck35.example.ddd.domain.bookstore.Bookshelf;
import de.ck35.example.ddd.jpa.NumericSequenceId;
import de.ck35.example.ddd.jpa.SpringEntityListener;

@Entity
@Table(name="bookshelf_space")
@EntityListeners(SpringEntityListener.class)
public class BookshelfSpaceEntity implements Bookshelf.Space {

    @EmbeddedId
    private NumericSequenceId id;
    
    @ManyToOne
    @JoinColumn(name="bookshelfId", referencedColumnName="id")
    private BookshelfEntity bookshelf;
    
    @OneToOne
    @JoinColumn(name="bookId", referencedColumnName="id")
    private BookEntity book;
    
    private int quantity;
    
    @Autowired transient BookshelfSpaceRepository bookshelfSpaceRepository;
    
    public BookshelfSpaceEntity() {
        this.id = new NumericSequenceId();
    }
    
    @Override
    public NumericSequenceId getId() {
        return id;
    }
    public void setId(NumericSequenceId id) {
        this.id = id;
    }
    public BookshelfEntity getBookshelf() {
        return bookshelf;
    }
    public void setBookshelf(BookshelfEntity bookshelf) {
        this.bookshelf = bookshelf;
    }
    @Override
    public BookEntity getBook() {
        return book;
    }
    public void setBook(BookEntity book) {
        this.book = book;
    }
    @Override
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public void take(int amount) {
        int newQuantity = quantity - amount;
        if(newQuantity < 0) {
            throw new IndexOutOfBoundsException("Negative quantity not allowed!");
        }
        if(newQuantity == 0) {
            bookshelfSpaceRepository.delete(this);
        } else {
            quantity = newQuantity;
            bookshelfSpaceRepository.save(this);
        }
    }
    
    public static interface BookshelfSpaceRepository extends CrudRepository<BookshelfSpaceEntity, NumericSequenceId> {

        BookshelfSpaceEntity findByBookshelfAndBook(BookshelfEntity bookshelfEntity, BookEntity book);
        
    }
}