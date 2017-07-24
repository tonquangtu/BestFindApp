package com.bk.bestfind.service;


import com.bk.bestfind.models.Product;

import java.util.List;

/**
 * Created by Dell on 24-Jun-17.
 */
public interface SearchListener {

    void complete(List<Product> products);

    void fail(Throwable t);
}
