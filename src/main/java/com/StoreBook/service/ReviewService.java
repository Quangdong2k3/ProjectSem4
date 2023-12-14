package com.StoreBook.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StoreBook.DTO.ReviewDTO;
import com.StoreBook.entity.Review;
import com.StoreBook.repository.ReviewRepository;

public interface ReviewService {
    List<Review> getAllBook(Long book_id);

    Review add(ReviewDTO r);

    Review reply(ReviewDTO r);

    Review status(ReviewDTO r);

    List<Review> getAll();

    void delete(Long id);
}

@Service
@Transactional
class implReviewService implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllBook(Long book_id) {

        // TODO Auto-generated method stub
        return reviewRepository.findByBook_IdAndStatus(book_id, 1);
    }

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Override
    public Review add(ReviewDTO r) {
        // TODO Auto-generated method stub
        Review re = new Review();
        re.setComment(r.getComment());
        re.setBook(bookService.getById1(r.getBook_id()));
        re.setRating(r.getRating());
        re.setStatus(0);
        re.setUser(userService.getByID(r.getUser_id()));

        return reviewRepository.save(re);
    }

    @Override
    public List<Review> getAll() {
        // TODO Auto-generated method stub
        return reviewRepository.findAll();

    }

    @Override
    public Review reply(ReviewDTO r) {
        // TODO Auto-generated method stub
        Review re = reviewRepository.findById(r.getId()).get();
        re.setReply(r.getReply());

        return reviewRepository.save(re);
    }

    @Override
    public Review status(ReviewDTO r) {
        // TODO Auto-generated method stub
        Review re = reviewRepository.findById(r.getId()).get();
            re.setStatus(r.getStatus());
       
        re.setUser2(userService.getByID(r.getAdmin_id()));
        return reviewRepository.save(re);
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        reviewRepository.deleteById(id);
    }
}
