package com.StoreBook.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.StoreBook.DTO.BookDTO;
import com.StoreBook.DTO.ReviewDTO;
import com.StoreBook.DTO.UserDTO;
import com.StoreBook.entity.Review;
import com.StoreBook.entity.User;
import com.StoreBook.service.CustomOAuth2User;
import com.StoreBook.service.MyRole;
import com.StoreBook.service.ReviewService;
import com.StoreBook.service.UserService;

@RestController
@RequestMapping("BookStore/api/review")
public class ReviewsController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("getAll/{book_id}")
    public ResponseEntity<List<ReviewDTO>> getAllBook(@PathVariable Long book_id) {

        List<ReviewDTO> lst = new ArrayList<>();
        for (Review review : reviewService.getAllBook(book_id)) {

            UserDTO udt1 = new UserDTO();
            udt1.setFullname(review.getUser().getFullname());
            udt1.setId(review.getUser().getId());
            UserDTO udt2 = new UserDTO();

            if (review.getUser2() != null) {
                udt2.setId(review.getUser2().getId());
                udt2.setFullname(review.getUser2().getFullname());
            }

            ReviewDTO rdt = ReviewDTO.builder()
                    .user(udt1)
                    .admin(udt2)
                    .rating(review.getRating())
                    .status(review.getStatus())
                    .comment(review.getComment())
                    .reply(review.getReply())
                    .created_at(review.getCreated_at())
                    .updated_at(review.getUpdate_at())
                    .build();
            lst.add(rdt);
        }

        return new ResponseEntity<List<ReviewDTO>>(lst, HttpStatus.OK);
    }

    @Autowired
    UserService userService;

    public Long getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long so = 1L;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;

                MyRole myRoleUser = (MyRole) userDetails;

                so = myRoleUser.getId();
            }
            if (principal instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) principal;
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) oAuth2User;
                User u = userService.findByEmail(customOAuth2User.getEmail());
                so = u.getId();
            }
        }
        return so;

        // Trả về một giá trị mặc định hoặc null tùy thuộc vào yêu cầu của bạn
    }

    @PostMapping("comment")
    public ResponseEntity<Review> addReview(@RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setUser_id(getUserID());
        return new ResponseEntity<Review>(reviewService.add(reviewDTO), HttpStatus.CREATED);
    }


    @GetMapping("getAllReview")
    public ResponseEntity<List<ReviewDTO>> getAllBook() {

        List<ReviewDTO> lst = new ArrayList<>();
        for (Review review : reviewService.getAll()) {

            
            UserDTO udt1 = new UserDTO();
            udt1.setFullname(review.getUser().getFullname());
            udt1.setId(review.getUser().getId());
            UserDTO udt2 = new UserDTO();

            if (review.getUser2() != null) {
                udt2.setId(review.getUser2().getId());
                udt2.setFullname(review.getUser2().getFullname());
            }

            BookDTO bookDTO = new BookDTO();
            bookDTO.setTitle(review.getBook().getTitle());

            ReviewDTO rdt = ReviewDTO.builder()
                    .id(review.getId())
                    .user(udt1)
                    .admin(udt2)
                    .rating(review.getRating())
                    .status(review.getStatus())
                    .comment(review.getComment())
                    .reply(review.getReply())
                    .book(bookDTO)
                    .created_at(review.getCreated_at())
                    .updated_at(review.getUpdate_at())
                    .build();
            lst.add(rdt);
        }

        return new ResponseEntity<List<ReviewDTO>>(lst, HttpStatus.OK);
    }

    @PutMapping("reply_comment")
    public ResponseEntity<Review>  reply(@RequestBody ReviewDTO reviewDTO){
        return new ResponseEntity<Review>(reviewService.reply(reviewDTO),HttpStatus.ACCEPTED);
    }

    @PutMapping("status_comment")
    public ResponseEntity<Review>  status(@RequestBody ReviewDTO reviewDTO){
        reviewDTO.setAdmin_id(getUserID());
        return new ResponseEntity<Review>(reviewService.status(reviewDTO),HttpStatus.ACCEPTED);
    }
    @PutMapping("delete_comment")
    public ResponseEntity<String>  deleteComment(@RequestBody ReviewDTO reviewDTO){
        reviewService.delete(reviewDTO.getId());
        return ResponseEntity.ok("Thành công xóa");
    }
}
