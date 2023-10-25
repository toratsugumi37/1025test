package com.kh.product.requestDAO;

import com.kh.product.requestForm.Product;
import com.kh.product.requestForm.RequestSaveForm;
import com.kh.product.requestForm.RequestUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestSVCImpl implements RequestSVC{

    private final RequestDAO requestDAO;
    @Override
    public Long saveProduct(RequestSaveForm requestSaveForm) {
        log.info("saveSVC호출");
        return requestDAO.saveProduct(requestSaveForm);
    }

    @Override
    public List<Product> getById(Long pid) {
        return requestDAO.getById(pid);
    }

    @Override
    public List<Product> getAllProduct() {
        return requestDAO.getAllProduct();
    }

    @Override
    public Long deleteProduct(Long pid) {
        return requestDAO.deleteProduct(pid);
    }

    @Override
    public Product updateProduct(Long pid, RequestUpdateForm requestUpdateForm) {
        return requestDAO.updateProduct(pid,requestUpdateForm);
    }
}
