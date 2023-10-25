package com.kh.product.requestDAO;

import com.kh.product.requestForm.Product;
import com.kh.product.requestForm.RequestSaveForm;
import com.kh.product.requestForm.RequestUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RequestDAOImpl implements RequestDAO{

    private final NamedParameterJdbcTemplate template;

    @Override
    public Long saveProduct(RequestSaveForm requestSaveForm) {
        log.info("saveDAO호출");
        StringBuffer sql = new StringBuffer();
        sql.append("insert into product(pid, pname, quantity, price) ");
        sql.append(             "values(pid_seq.nextval, :pname, :quantity, :price) ");

        SqlParameterSource param2 = new BeanPropertySqlParameterSource(requestSaveForm);
        Map<String,Object> param = Map.of("pname",requestSaveForm.getPname(),"quantity",requestSaveForm.getQuantity(),"price",requestSaveForm.getPrice());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int savedRow = template.update(sql.toString(),param2,keyHolder,new String[]{"pid"});
        log.info("savedRow = {}",savedRow);
        Long productId = keyHolder.getKey().longValue();
        log.info("DAO's pid={}",productId);
        return productId;
    }

    @Override
    public List<Product> getById(Long pid) {
        log.info("getById호출");
        StringBuffer sql = new StringBuffer();
        sql.append(" select * from product where pid = :pid ");

        Map<String,Long> param = Map.of("pid",pid);
        List<Product> gettedRow = template.query(sql.toString(),param,new BeanPropertyRowMapper<>(Product.class));
        log.info("gettedRow:{}",gettedRow);
        return gettedRow;
    }

    @Override
    public List<Product> getAllProduct() {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from product ");
        sql.append(  "order by pid desc ");

        List<Product> allProduct = template.query(sql.toString(),new BeanPropertyRowMapper<>(Product.class));

        return allProduct;
    }

    @Override
    public Long deleteProduct(Long pid) {

        StringBuffer sql = new StringBuffer();
        sql.append(" delete from product where pid = :pid ");

        SqlParameterSource param1 = new MapSqlParameterSource().addValue("pid",pid);

        int deletedRow = template.update(sql.toString(),param1);


        if(pid>=1){
            return pid;
        } else {
            return 0L;
        }
    }

    @Override
    public Product updateProduct(Long pid, RequestUpdateForm requestUpdateForm) {
        StringBuffer sql = new StringBuffer();
        sql.append("update product set pname = :pname, ");
        sql.append("quantity = :quantity, price = :price ");
        sql.append("where pid = :pid ");

        SqlParameterSource param = new MapSqlParameterSource().addValue("pname",requestUpdateForm.getPname())
                .addValue("quantity",requestUpdateForm.getQuantity())
                .addValue("price",requestUpdateForm.getPrice())
                .addValue("pid",pid);

        Product product = new Product();
        product.setPid(pid);
        product.setPname(requestUpdateForm.getPname());
        product.setQuantity(requestUpdateForm.getQuantity());
        product.setPrice(requestUpdateForm.getPrice());

        template.update(sql.toString(),param);

        return product;
    }
}

