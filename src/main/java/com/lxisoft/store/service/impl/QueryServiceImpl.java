package com.lxisoft.store.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lxisoft.store.domain.Product;
import com.lxisoft.store.service.QueryService;
@Service
@Transactional
public class QueryServiceImpl implements QueryService {
 
	@Override
	public  List<Product> findAllProductsByCategoryId(long categoryId) {
		 
		
		
		
		return null;
	}

}
