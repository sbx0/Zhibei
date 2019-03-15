package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Product;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController<Product, Integer> {
    @Resource
    private ProductService productService;

    @Override
    public BaseService<Product, Integer> getService() {
        return productService;
    }

    @Autowired
    public ProductController(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
