# example-ddd-with-spring-data-jpa
Minimal project for DDD implemented with Spring Data JPA.

###The domain model
This project demonstrates how an application with a rich domain model should be implemented with Spring Data JPA.
The whole domain model is build with plain Java interfaces inside the package: de.ck35.example.ddd.domain.bookstore.
Repositories or other manager classes should be avoided here as we build an abstraction of reality not of technical
circumstances. Also no JPA related annotations are included inside the domain model.

The model describes a simple bookstore with categorized bookshelves and books which can be added and removed.


###The model implementation
After the model has been defined we want to implement the interfaces with Spring Data JPA. For better separation
the implementation is put into its own package path: de.ck35.example.ddd.jpa.bookstore. This implementation is
JPA specific so this technology name is also part of the package name.