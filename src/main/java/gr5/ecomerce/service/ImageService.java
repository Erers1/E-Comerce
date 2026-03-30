package gr5.ecomerce.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ResponseEntity<List<String>> uploadProductImage(Long product_id, List<MultipartFile> file, String filename);
    ResponseEntity<String> setAvarta(Long user_id, MultipartFile file, String filename);
    void deleteImage(String img);
}
