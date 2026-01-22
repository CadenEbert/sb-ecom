package com.ecommerce.project.service;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);
    ProductResponse getAllProducts();
    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchByKeyword(String keyword);

    ProductDTO updateProduct(@Valid ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;


}
