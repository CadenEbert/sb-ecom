package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;


    @Value("${project.image}")
    private String path;



    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productdto) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));


        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();

        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(productdto.getProductName())) {
                isProductNotPresent = false;
                break;
            }

        }

        if (isProductNotPresent) {
            Product product = modelMapper.map(productdto, Product.class);
            product.setCategory(category);
            product.setImage("default.png");
            double specialPrice = product.getPrice() - (product.getDiscount() * .01) * product.getPrice();
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        } else {
            throw new APIException("Product already exists");
        }


    }

    @Override
    public ProductResponse getAllProducts() {

        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        if (productDTOS.isEmpty()) {
            throw new APIException("No products exist");
        }
        ProductResponse productResponse = new ProductResponse(productDTOS);
        productResponse.setContent(productDTOS);
        return productResponse;

    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        if (productDTOS.isEmpty()) {
            throw new APIException("No products exist");
        }
        ProductResponse productResponse = new ProductResponse(productDTOS);
        productResponse.setContent(productDTOS);
        return productResponse;

    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase(keyword);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        if (productDTOS.isEmpty()) {
            throw new APIException("No products exist");
        }
        ProductResponse productResponse = new ProductResponse(productDTOS);
        productResponse.setContent(productDTOS);
        return productResponse;

    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));


        savedProduct.setProductName(product.getProductName());
        savedProduct.setPrice(product.getPrice());
        savedProduct.setDiscount(product.getDiscount());
        savedProduct.setCategory(product.getCategory());
        savedProduct.setImage(product.getImage());
        savedProduct.setDescription(product.getDescription());
        savedProduct.setQuantity(product.getQuantity());
        savedProduct.setSpecialPrice(product.getSpecialPrice());
        savedProduct = productRepository.save(savedProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);

    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        String fileName = fileService.uploadImage(path, image);
        productDB.setImage(fileName);

        Product savedProduct = productRepository.save(productDB);
        return modelMapper.map(savedProduct, ProductDTO.class);

    }



}
