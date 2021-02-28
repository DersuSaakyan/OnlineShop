package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(name = "product_id")
    private  int prId;
    @Column(name = "user_id")
    private int usId;
    @Column
    private int quantity;
    @Column
    private String date;
    @Column
    private double total;
    @Column(name = "pic_url")
    private String picUrl;
    @Column(name = "pr_title")
    private String productTitle;

}
