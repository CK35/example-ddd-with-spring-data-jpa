package de.ck35.example.ddd.jpa;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import de.ck35.example.ddd.domain.Entity;

/**
 * Simple implementation of an numeric Id of type long.
 *
 * @author Christian Kaspari
 * @since 1.0.0
 */
@Embeddable
public class NumericSequenceId implements Entity.Id {

    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long value;
    
    public Long getValue() {
        return value;
    }
    public void setValue(Long value) {
        this.value = value;
    }
    
    @Override
    public String displayValue() {
        return value == null ? "-" : Long.toString(value);
    }
    
    @Override
    public String toString() {
        return displayValue();
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NumericSequenceId)) {
            return false;
        }
        NumericSequenceId other = (NumericSequenceId) obj;
        return Objects.equals(value, other.value);
    }
    
}