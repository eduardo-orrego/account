package com.nttdata.account.business.impl;

import com.nttdata.account.business.ProductService;
import com.nttdata.account.client.ProductClient;
import com.nttdata.account.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductClient productClient;

    @Override
    public Flux<Product> findProducts(String typeProduct) {
        return productClient.getProducts(typeProduct)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No se encontraron datos del producto"))))
            .doOnComplete(() -> log.info("Successful find Products - Type: ".concat(typeProduct)));
    }

}
