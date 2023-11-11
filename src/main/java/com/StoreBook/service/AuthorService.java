package com.StoreBook.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StoreBook.entity.Author;
import com.StoreBook.repository.AuthorRepository;

public interface AuthorService {
    void add(Author author);

    void update(Long id, Author author);

    void delete(Long id);

    Author getById(Long id);

    List<Author> getAll();

}

@Transactional
@Service
class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public void add(Author author) {
        // TODO Auto-generated method stub
        authorRepository.save(author);
    }

    @Override
    public void update(Long id, Author author) {
        // TODO Auto-generated method stub
        Author a = authorRepository.findById(id).get();
        if (a != null) {
            a.setName(author.getName());
            a.setBiography(author.getBiography());
        }
        authorRepository.save(a);
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        authorRepository.deleteById(id);
    }

    @Override
    public Author getById(Long id) {
        // TODO Auto-generated method stub
        return authorRepository.findById(id).get();
    }

    @Override
    public List<Author> getAll() {
        // TODO Auto-generated method stub
        List<Author> author = new ArrayList<>();
        authorRepository.findAll().forEach(author::add);
        return author;

    }

}
