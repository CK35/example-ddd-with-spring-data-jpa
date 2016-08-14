package de.ck35.example.ddd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Splitter;

import de.ck35.example.ddd.configuration.JpaConfiguration;
import de.ck35.example.ddd.domain.bookstore.Book;
import de.ck35.example.ddd.domain.bookstore.Bookshelf;
import de.ck35.example.ddd.domain.bookstore.Bookshelf.Space;
import de.ck35.example.ddd.domain.bookstore.Bookstore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JpaConfiguration.class })
@TestPropertySource("classpath:test-h2-context.properties")
public class BookstoreTest {

    @Autowired ResourceLoader loader;
    @Autowired DataSource dataSource;
    @Autowired EntityManager entityManager;

    @Autowired Bookstore bookstore;

    @Test
    public void testBookstore() {
        String expectedBookTitle = "Domain-Driven Design";

        bookstore.addBookshelf("Computer science").store(bookstore.register(expectedBookTitle, 12.5), 5);

        Bookshelf shelf = bookstore.findShelf("Computer science").get();
        Space space = shelf.findBook(expectedBookTitle).get();
        Book book = space.getBook();
        
        assertEquals(expectedBookTitle, book.getTitle());
        assertEquals(12.5, book.getPrice(), 0d);
        assertEquals(5, space.getQuantity());
        
        space = shelf.store(book, 5);
        space.take(5);
        assertEquals(5, space.getQuantity());
        assertEquals(5, shelf.findBook(expectedBookTitle).get().getQuantity());
        space.take(5);
        assertFalse(shelf.findBook(expectedBookTitle).isPresent());
        
        assertFalse(bookstore.findShelf("Nature").isPresent());
    }

    @Before
    public void before() throws Exception {
        execute(Files.readAllBytes(loader.getResource("classpath:create_schema.sql").getFile().toPath()));
        execute(Files.readAllBytes(loader.getResource("classpath:create_tables.sql").getFile().toPath()));
    }

    @After
    public void after() throws Exception {
        execute(Files.readAllBytes(loader.getResource("classpath:drop_schema.sql").getFile().toPath()));
    }

    protected void evictAllCaches() {
        entityManager.getEntityManagerFactory().getCache().evictAll();
    }

    private void execute(byte[] bytes) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            Splitter.on("\n\n").trimResults().omitEmptyStrings().split(new String(bytes, StandardCharsets.UTF_8)).forEach(sql -> {
                try (Statement statement = conn.createStatement()) {
                    statement.execute(sql);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}