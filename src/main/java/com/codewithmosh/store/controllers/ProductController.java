package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
  private  final ProductRepository productRepository;
  private final ProductMapper productMapper;
  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts(
          @RequestParam(required = false,name = "categoryId") Byte categoryId
  ){
    List<Product> productList;
    if(categoryId != null){
      productList = productRepository.findByCategoryId(categoryId);
    }else {
      productList = productRepository.findAllWithCategory();
    }
    List<ProductDto> productDtoList = productList.stream()
            .map(productMapper::toDto)
            .toList();
    return ResponseEntity.ok(productDtoList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProduct(
          @PathVariable Long id
  ){
    ProductDto productDto = productMapper.toDto(productRepository.findById(id).orElse(null));
    if(productDto == null){
      return  ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productDto);
  }
}
