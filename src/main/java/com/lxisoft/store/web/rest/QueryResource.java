/**
 * 
 */
package com.lxisoft.store.web.rest;

import com.lxisoft.store.service.CartService;
import com.lxisoft.store.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lxisoft.store.service.dto.CartDTO;
import com.lxisoft.store.service.dto.ProductDTO;

/**
 * 
 *
 */
@RestController
@RequestMapping("/api/query")
public class QueryResource {

	private final Logger log = LoggerFactory.getLogger(QueryResource.class);
	@Autowired
	ProductService productService;
	@Autowired
	private CartService cartService;

	/**
	 * To get all product under particular category by using category id Way:After
	 * getting all product ,check against given category id
	 */
	@GetMapping("/findAllProductsByCategoryId/{categoryId}")
	public List<ProductDTO> findAllProductsByCategoryId(@PathVariable Long categoryId) {

		log.debug("<<<<< findAllProductsByCategoryId >>>>>>");
		List<ProductDTO> resultantproduct = new ArrayList<ProductDTO>();
		List<ProductDTO> productList = productService.findAll();
		for (ProductDTO product : productList) {
			if (product.getCategoryId() == categoryId) {
				resultantproduct.add(product);
			}
		}
		return resultantproduct;
	}

	/**
	 * To get number of stocks by using particular category id
	 *
	 */
	@GetMapping("/findStockByCategoryId/{categoryId}")
	public long findStockByCategoryId(@PathVariable Long categoryId) {
		List<ProductDTO> productList = productService.findAll();
		long totalStock = 0;
		for (ProductDTO product : productList) {
			if (product.getCategoryId() == categoryId) {
				totalStock = totalStock + product.getNoOfStock();
			}
		}
		return totalStock;
	}

	/**
	 * To get number of stocks by using particular product id
	 *
	 */
	@GetMapping("/findStockByProductId/{productId}")
	public long findStockByProductId(@PathVariable Long productId) {
		log.debug("<<<<< findStockByProductId >>>>>>");
		return productService.findOne(productId).get().getNoOfStock();
	}

	@GetMapping("/findAllCartByCustomerId/{customerId}")
	public List<CartDTO> findAllCartByCustomerId(@PathVariable Long customerId) {
		log.debug("<<<<< findAllCartByCustomerId >>>>>>");
		List<CartDTO> cartList = cartService.findAll();		
		List<CartDTO> cartListResult=new ArrayList<CartDTO>(); 
		 for (int i=0;i< cartList.size();i++) {log.debug("<<<<< tes >>>>>>");
			if (cartList.get(i).getCustomerId() == customerId) {
				cartListResult.add(cartList.get(i));
				log.debug("<<<<< la >>>>>>");
			}
		 }
		return cartListResult;
	}

}
