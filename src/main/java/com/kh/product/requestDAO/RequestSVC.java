package com.kh.product.requestDAO;

import com.kh.product.requestForm.Product;
import com.kh.product.requestForm.RequestSaveForm;
import com.kh.product.requestForm.RequestUpdateForm;

import java.util.List;

public interface RequestSVC {
    Long saveProduct(RequestSaveForm requestSaveForm);
    List<Product> getById(Long pid);
    List<Product> getAllProduct();
    Long deleteProduct(Long pid);
    Product updateProduct(Long pid, RequestUpdateForm requestUpdateForm);
}
