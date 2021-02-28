package com.example.demo.repository;

import com.example.demo.model.Product;
import com.example.demo.model.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg,Integer> {
    List<ProductImg>  findAllByProductId(int i);
}
