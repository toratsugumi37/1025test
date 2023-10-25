package com.kh.product.requestForm;

import lombok.Data;

@Data
public class Product {
    private Long pid;
    private String pname;
    private Long quantity;
    private Long price;
}
