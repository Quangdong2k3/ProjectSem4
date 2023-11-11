package com.StoreBook.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StoreBook.entity.Publisher;
import com.StoreBook.repository.PublisherRepository;

public interface PublisherService {
    void add(Publisher p);

    void update(Long id, Publisher p);

    void delete(Long id);

    Publisher getById(Long id);

    List<Publisher> getAll();

}

@Transactional
@Service
class PubliserServiceImpl implements PublisherService {
    @Autowired
    PublisherRepository publishersRepository;

    @Override
    public void add(Publisher p) {
        // TODO Auto-generated method stub
        if (p != null) {
            publishersRepository.save(p);
        }
    }

    @Override
    public void update(Long id, Publisher p) {
        // TODO Auto-generated method stub
        if (id != 0) {
            p.setId(id);
            publishersRepository.save(p);
        }
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        publishersRepository.deleteById(id);
    }

    @Override
    public Publisher getById(Long id) {
        // TODO Auto-generated method stub
        return id != 0 ? publishersRepository.findById(id).get() : null;
    }

    @Override
    public List<Publisher> getAll() {
        // TODO Auto-generated method stub
        return publishersRepository.findAll();

    }

}
