package gr5.ecomerce.service.Implements;

import com.cloudinary.utils.ObjectUtils;
import gr5.ecomerce.config.CloudinaryConfig;
import gr5.ecomerce.entity.Product;
import gr5.ecomerce.entity.ProductImage;
import gr5.ecomerce.entity.User;
import gr5.ecomerce.repository.ProductRepository;
import gr5.ecomerce.repository.UserRepository;
import gr5.ecomerce.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageServiceImplement implements ImageService {
    private final CloudinaryConfig cloudinaryConfig;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<List<String>> uploadProductImage(Long product_id, List<MultipartFile> file, String filename) {
        List<String> url = new ArrayList<>();
        List<ProductImage> images = new ArrayList<>();
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        for (MultipartFile multipartFile : file) {
            try {
                Map result = cloudinaryConfig.cloudinary().uploader().upload(
                        multipartFile.getBytes(),
                        ObjectUtils.asMap(
                                "folder", filename,
                                "resource_type", "auto"
                        )
                );
                url.add((String) result.get("secure_url"));
            } catch (IOException e) {
                throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage());
            }
        }
        for (String u : url) {
            ProductImage productImage = ProductImage.builder()
                    .url(u)
                    .product(product)
                    .build();
            images.add(productImage);
        }
        product.getImg().clear();
        product.getImg().addAll(images);
        productRepository.save(product);
        return ResponseEntity.ok(url);
    }

    @Override
    public ResponseEntity<String> setAvarta(Long user_id, MultipartFile file, String filename) {
        User user = userRepository.findById(user_id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        try {
            Map result = cloudinaryConfig.cloudinary().uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", filename,
                            "resource_type", "auto"
                    )
            );
            user.setAvatar((String) result.get("secure_url"));
            return ResponseEntity.ok((String) result.get("secure_url"));
        } catch (IOException e) {
            throw new RuntimeException("Upload ảnh thất bại: " + e.getMessage());
        }
    }

    @Override
    public void deleteImage(String img) {
        try {
            cloudinaryConfig.cloudinary().uploader().destroy(img, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Xóa ảnh thất bại: " + e.getMessage());
        }
    }
}
