package com.StoreBook.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StoreBook.DTO.BookAuthor;
import com.StoreBook.DTO.BookDTO;
import com.StoreBook.entity.Author;
import com.StoreBook.entity.Book;
import com.StoreBook.entity.Category;
import com.StoreBook.entity.Publisher;
import com.StoreBook.repository.AuthorRepository;
import com.StoreBook.repository.BookRepository;
import com.StoreBook.repository.CategoryRepository;
import com.StoreBook.repository.PublisherRepository;

public interface BookService {
    void add(BookDTO book) throws ParseException;

    void update(Long id, BookDTO b) throws ParseException;

    void delete(Long id);

    void AuthordeleteByBookid(Long bid);

    BookDTO getById(Long id);
Book getById1(Long id);
    List<Book> getAll();

    void addBookAuthor(BookAuthor bookauthor);
}

@Transactional
@Service
class BookServiceImpl implements BookService {
    private SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    BookRepository booksRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    AuthorService authorService;

    @Override
    public void add(BookDTO book) throws ParseException {
        // TODO Auto-generated method stub
        Book b = new Book();
        b.setDescription(book.getDescription());
        Category c = categoryRepository.findById((long) book.getCategory_id()).get();
        b.setCategory(c);
        Publisher p = publisherRepository.findById((long) book.getPublisher_id()).get();
        b.setPublisher(p);
        b.setTitle(book.getTitle());
        b.setQuantity(book.getQuantity());
        b.setPrice(book.getPrice());
        b.setImage(book.getImage());
        System.out.println(book.getAuthor_id());
        // b.getAuthors().add(authorService.getById(book.getAuthor_id()));

        Date date = inputDateFormat.parse(book.getPublication_date());
        String outputDate = outputDateFormat.format(date);
        Date date1 = outputDateFormat.parse(outputDate);

        b.setPublication_date(date1);
        booksRepository.save(b);
    }

    @Override
    public void update(Long id, BookDTO b) throws ParseException {
        // TODO Auto-generated method stub
        Book be = booksRepository.findById(id).get();

        if (id != 0) {
            be.setDescription(b.getDescription());
            be.setCategory(categoryRepository.findById(b.getCategory_id()).get());
            be.setPrice(b.getPrice());
            be.setImage(b.getImage());
            be.setPublisher(publisherRepository.findById(b.getPublisher_id()).get());
            Date date = inputDateFormat.parse(b.getPublication_date());
            String outputDate = outputDateFormat.format(date);
            Date date1 = outputDateFormat.parse(outputDate);
            be.setPublication_date(date1);
            be.setQuantity(b.getQuantity());
            be.setTitle(b.getTitle());
            be.getAuthors().add(authorService.getById(b.getAuthor_id()));

            booksRepository.save(be);
        }
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        booksRepository.deleteById(id);
    }

    @Override
    public BookDTO getById(Long id) {
        // TODO Auto-generated method stub
        Book b = booksRepository.findById(id).get();
        BookDTO dto = new BookDTO();
        dto.setId(b.getId());
        dto.setDescription(b.getDescription());
        dto.setImage(b.getImage());
        dto.setPrice(b.getPrice());

        SimpleDateFormat outputDate = new SimpleDateFormat("dd-MM-yyyy");

        String d1 = outputDate.format(b.getPublication_date());
        dto.setPublication_date(d1);
        dto.setPublisher_id(b.getPublisher().getId());
        dto.setTitle(b.getTitle());
        dto.setQuantity(b.getQuantity());
        dto.setCategory_id(b.getCategory().getId());
        return dto;
    }

    @Override
    public List<Book> getAll() {
        // TODO Auto-generated method stub
        return booksRepository.findAll();
    }

    @Override
    public void addBookAuthor(BookAuthor bookauthor) {
        // TODO Auto-generated method stub
        if (bookauthor != null) {
            // booksRepository
            Author a = authorService.getById(bookauthor.getAuthorId());
            Book b = booksRepository.findById(bookauthor.getBookId()).get();

            b.getAuthors().add(a);
            booksRepository.save(b);
        }
    }

    @Autowired
    AuthorRepository authorRepository;
@PersistenceContext
    private EntityManager entityManager;
    @Override
    public void AuthordeleteByBookid(Long bid) {
        // TODO Auto-generated method stube
        Book b = booksRepository.findById(bid).get();
          Set<Author> authors = new HashSet<>(b.getAuthors());
        for (Author author : authors) {
            b.getAuthors().remove(author);
            author.getBooks().remove(b);
        }
        entityManager.flush();

    }

    @Override
    public Book getById1(Long id) {
        // TODO Auto-generated method stub
        return booksRepository.findById(id).get();
    }

}
