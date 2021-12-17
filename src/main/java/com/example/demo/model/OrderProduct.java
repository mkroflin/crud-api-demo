package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderProductsId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderProduct() {
    }

    public OrderProduct(double price, Product product, Order order) {
        this.price = price;
        this.product = product;
        this.order = order;
    }

    public double getPrice() {
        return price;
    }
}