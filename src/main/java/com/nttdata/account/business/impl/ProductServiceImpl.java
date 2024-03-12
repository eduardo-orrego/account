package com.nttdata.account.business.impl;

import com.nttdata.account.business.ProductService;
import com.nttdata.account.client.ProductClient;
import com.nttdata.account.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductClient productClient;

    @Override
    public Flux<Product> getProduct(String productType) {
        return productClient.getProduct(productType);
    }
}
