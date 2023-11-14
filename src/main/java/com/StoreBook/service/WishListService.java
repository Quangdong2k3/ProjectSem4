package com.StoreBook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StoreBook.DTO.BookDTO;
import com.StoreBook.DTO.WishList;
import com.StoreBook.entity.Book;
import com.StoreBook.repository.BookRepository;

public interface WishListService {

    void addItem(WishList w);

    void removeItem(int id);

    List<WishList> getItems();

    void editItem(WishList w);
}

@Service
@Transactional
class WishListServiceImpl implements WishListService {
    List<WishList> items = new ArrayList<WishList>();
    @Autowired
    BookRepository bookRepository;

    @Override
    public void addItem(WishList w) {
        // TODO Auto-generated method stub
        if(items.size()>0){
            for (WishList i : items) {
                if (i.getBook().getId() == w.getB_id()) {
                    i.setQuantity(i.getQuantity() + w.getQuantity());
                    items.set((int) i.getId(), i);
                    break;
                } 
            }
        }else{
            Book b = bookRepository.findById(w.getB_id()).get();
            BookDTO dt = new BookDTO();
            dt.setId(b.getId());
            dt.setTitle(b.getTitle());
            dt.setDescription(b.getDescription());
            dt.setPrice(b.getPrice());
            dt.setImage(b.getImage());
            w.setBook(dt);
            w.setId(items.size());
            items.add(w);
        }
        
        
          
    }

    @Override
    public List<WishList> getItems() {
        // TODO Auto-generated method stub
        return items;
    }

    @Override
    public void removeItem(int id) {

        items.remove(id);
    }

    @Override
    public void editItem(WishList w) {
        // TODO Auto-generated method stub
        Book b = bookRepository.findById(w.getB_id()).get();
        BookDTO dt = new BookDTO();
        dt.setId(b.getId());
        dt.setTitle(b.getTitle());
        dt.setDescription(b.getDescription());
        dt.setPrice(b.getPrice());
        dt.setImage(b.getImage());
        w.setBook(dt);
        w.setId(w.getId());
        items.set((int) w.getId(), w);
    }

}
