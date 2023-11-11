package com.StoreBook.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.StoreBook.entity.Image;
import com.StoreBook.repository.BookRepository;
import com.StoreBook.repository.ImageRepository;

@Service
public class ImageService {

	@Value("${spring.servlet.multipart.location}") // Define the path to store image files in application.properties
    private String uploadPath;

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private BookService bookService;
    
@Autowired
BookRepository bookRepository;
    public void saveImages(MultipartFile[] files, Long bookId) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String filePath = Paths.get(uploadPath, originalFilename).toString();
                
                if (!imageRepository.existsByImgone(originalFilename) && !imageRepository.existsByImgtwo(originalFilename) && 
                    !imageRepository.existsByImgthree(originalFilename) && !imageRepository.existsByImgfour(originalFilename) && 
                    !imageRepository.existsByImgfive(originalFilename)) {
                    
                    try {
                        Files.write(Paths.get(filePath), file.getBytes());
                        
                        // Create an Image entity and set the appropriate field (imgone, imgtwo, etc.)
                        Image image = new Image();
                        image.setBook(bookRepository.findById(bookId).get()); // Get the associated book entity
                        
                        if (image.getImgone() == null) {
                            image.setImgone(originalFilename);
                        } else if (image.getImgtwo() == null) {
                            image.setImgtwo(originalFilename);
                        } else if (image.getImgthree() == null) {
                            image.setImgthree(originalFilename);
                        } else if (image.getImgfour() == null) {
                            image.setImgfour(originalFilename);
                        } else if (image.getImgfive() == null) {
                            image.setImgfive(originalFilename);
                        }
                        imageRepository.save(image);
                    } catch (IOException e) {
                        // Handle the exception appropriately
                    }
                }
            }
        }
    }
}
