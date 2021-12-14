package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String name)
    {
        try {
            List<Product> products = new ArrayList<Product>();

            if (name == null)
                productRepository.findAll().forEach(products::add);
            else
                productRepository.findByName(name).forEach(products::add);

            if (products.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product)
    {
        try {
            Product newProduct = productRepository.save(new Product(product.getName(), product.getPrice()));
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product)
    {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent())
        {
            Product newProduct = productData.get();
            newProduct.setName(product.getName());
            newProduct.setPrice(product.getPrice());
            return new ResponseEntity<>(productRepository.save(newProduct), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}