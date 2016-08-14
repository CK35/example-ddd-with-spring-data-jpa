package de.ck35.example.ddd.domain.bookstore;

import de.ck35.example.ddd.domain.Entity;

/**
 * A simple book entity with a title and price. 
 *
 * @author Christian Kaspari
 * @since 1.0.0
 */
public interface Book extends Entity {
    
    /**
     * @return The title of the book.
     */
    String getTitle();
    
    /**
     * @return The price of the book.
     */
    double getPrice();
    
}