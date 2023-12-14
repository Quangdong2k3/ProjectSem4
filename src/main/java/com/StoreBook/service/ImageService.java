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
BookRepository bookRepository;
    public void saveImages(MultipartFile[] files, Long bookId) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String filePath = Paths.get(uploadPath, originalFilename).toString();
                
                if (!imageRepository.existsByImg1(originalFilename) && !imageRepository.existsByImg2(originalFilename) && 
                    !imageRepository.existsByImg3(originalFilename) && !imageRepository.existsByImg4(originalFilename) && 
                    !imageRepository.existsByImg5(originalFilename)) {
                    
                    try {
                        Files.write(Paths.get(filePath), file.getBytes());
                        
                        // Create an Image entity and set the appropriate field (imgone, imgtwo, etc.)
                        Image image = new Image();
                        image.setBook(bookRepository.findById(bookId).get()); // Get the associated book entity
                        
                        if (image.getImg1() == null) {
                            image.setImg1(originalFilename);
                        } else if (image.getImg2() == null) {
                            image.setImg2(originalFilename);
                        } else if (image.getImg3() == null) {
                            image.setImg3(originalFilename);
                        } else if (image.getImg4() == null) {
                            image.setImg4(originalFilename);
                        } else if (image.getImg5() == null) {
                            image.setImg5(originalFilename);
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
