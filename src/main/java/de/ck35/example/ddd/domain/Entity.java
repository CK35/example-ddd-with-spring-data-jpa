package de.ck35.example.ddd.domain;

import java.io.Serializable;

/**
 * Base interface for all entities with an id. 
 *
 * @author Christian Kaspari
 * @since 1.0.0
 */
public interface Entity {

    /**
     * @return The primary id (key).
     */
    Id getId();

    /**
     * A serializable id for an entity.
     *
     * @author Christian Kaspari
     * @since 1.0.0
     */
    interface Id extends Serializable {

        /**
         * @return A display value for this id.
         */
        String displayValue();
        
    }
}