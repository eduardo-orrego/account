package com.nttdata.account.business;

import com.nttdata.account.model.Product;
import reactor.core.publisher.Flux;

public interface ProductService {
    Flux<Product> getProduct(String productType);
}
