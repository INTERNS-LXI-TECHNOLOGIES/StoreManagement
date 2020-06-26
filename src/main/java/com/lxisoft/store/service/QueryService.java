package com.lxisoft.store.service;
import com.lxisoft.store.domain.Product;

import java.util.List;
 

public interface QueryService {
	 List<Product>  findAllProductsByCategoryId(long categoryId);
}
