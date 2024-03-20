package com.nttdata.account.business;

import com.nttdata.account.model.Product;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Product> findProduct(String typeProduct);

}
