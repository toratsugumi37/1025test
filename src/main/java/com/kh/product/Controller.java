package com.kh.product;

import com.kh.product.requestDAO.RequestSVC;
import com.kh.product.requestForm.Product;
import com.kh.product.requestForm.RequestSaveForm;
import com.kh.product.requestForm.RequestUpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class Controller {

//    private final RequestSaveForm requestSaveForm;
//    private final RequestUpdateForm requestUpdateForm;

    private final RequestSVC requestSVC;

    @GetMapping("/")
    public String hi(){

        return "gd";
    }

    @ResponseBody
    @PostMapping("/save")
    public ApiResponse<Object> view(@RequestBody @Valid RequestSaveForm requestSaveForm, BindingResult bindingResult){

        ApiResponse<Object> res = null;

        if(bindingResult.hasErrors()){
            log.info("bindingresult = {}",bindingResult);
            res = ApiResponse.createApiResponse("01","실패",null);
            return res;
        }
        log.info("requestSaveForm={}",requestSaveForm);
        Long pid = requestSVC.saveProduct(requestSaveForm);
        List<Product> gettedProduct = requestSVC.getById(pid);
        log.info("gettedProduct={}",gettedProduct);

        res = ApiResponse.createApiResponse("00","성공",gettedProduct);

        return res;
    }

    @ResponseBody
    @GetMapping("/{pid}")
    public ApiResponse<Object> search(@PathVariable Long pid){

        ApiResponse<Object> res = null;
        List<Product> searchedProduct = requestSVC.getById(pid);
        if(searchedProduct.isEmpty()){
            res = ApiResponse.createApiResponse("01","조회된 열이 없습니다.",null);
            return res;
        }
        res = ApiResponse.createApiResponse("00","성공",searchedProduct);

        return res;
    }

    @ResponseBody
    @GetMapping("/all")
    public ApiResponse<Object> all(){
        ApiResponse<Object> res= null;
        List<Product> allList = requestSVC.getAllProduct();
        if(allList.isEmpty()){
            res = ApiResponse.createApiResponse("01","데이터가 없습니다.",allList);
            return res;
        }
        res = ApiResponse.createApiResponse("00","성공",allList);
        return res;
    }

    @ResponseBody
    @DeleteMapping("/{pid}")
    public ApiResponse<Object> delete(@PathVariable Long pid){
        ApiResponse<Object> res = null;
        List<Product> willDeleteRow = requestSVC.getById(pid);
        Long deletedPid = requestSVC.deleteProduct(pid);
        if(willDeleteRow.isEmpty()){
            res=ApiResponse.createApiResponse("01","실패",null);
            return res;
        }
        res = ApiResponse.createApiResponse("00","성공, 조회된 열은 삭제되었습니다.",willDeleteRow);
        return res;
    }

    @ResponseBody
    @PatchMapping("/{pid}")
    public ApiResponse<Object> update(@PathVariable("pid") Long pid,
                                      @RequestBody @Valid RequestUpdateForm requestUpdateForm,
                                      BindingResult bindingResult){
        ApiResponse<Object> res = null;
        if(bindingResult.hasErrors()){
            res = ApiResponse.createApiResponse("01","업데이트실패(범위값오류)",null);
            return res;
        }
        Product product = requestSVC.updateProduct(pid,requestUpdateForm);
        log.info("업데이트될 product{}",product);
        if(product.getPid() >= 1){
            res = ApiResponse.createApiResponse("00","업데이트성공",product);
        } else {
            res = ApiResponse.createApiResponse("01","업데이트실패",null);
        }

        return res;
    }



}
