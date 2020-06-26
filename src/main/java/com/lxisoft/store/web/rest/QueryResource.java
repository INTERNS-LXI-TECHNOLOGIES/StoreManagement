/**
 * 
 */
package com.lxisoft.store.web.rest;

import com.lxisoft.store.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lxisoft.store.domain.Category;
import com.lxisoft.store.domain.Product;
import com.lxisoft.store.service.CategoryService;
import com.lxisoft.store.service.QueryService;
import com.lxisoft.store.service.SaleService;
import com.lxisoft.store.service.StockService;
import com.lxisoft.store.service.dto.CategoryDTO;
import com.lxisoft.store.service.dto.ProductDTO;
import com.lxisoft.store.service.dto.SaleDTO;

/**
 * 
 *
 */
@RestController
@RequestMapping("/api/query")
public class QueryResource {

	private final Logger log = LoggerFactory.getLogger(QueryResource.class);

	@Autowired
	CategoryService categoryService;
	@Autowired
	QueryService queryService;
	@Autowired
	ProductService productService;
	@Autowired
	StockService stockService;
	@Autowired
	SaleService saleService;
	/**
	 * To return all list of categories
	 *
	 */
	@GetMapping("/findAllCategories")
	public List<CategoryDTO> findAllCategories() {
		log.debug("<<<<< findAllCategories >>>>>>");
		return categoryService.findAll();
	}
	/**
	 * To get a category by using particular category id
	 *
	 */
	@GetMapping("/findCategory/{categoryId}")
	public  CategoryDTO  findCategory(@PathVariable String categoryId) {
		log.debug("<<<<< findCategory >>>>>>");
		long id = Long.parseLong(categoryId);
		return categoryService.findOne(id).get();
	}
	/**
	 * To get a product by using particular product id
	 *
	 */
	@GetMapping("/findProduct/{productId}")
	public  ProductDTO  findProduct(@PathVariable String productId) {
		log.debug("<<<<< findProduct >>>>>>");
		long id = Long.parseLong(productId);
		return productService.findOne(id).get();
	}
	/**
	 * To get all product under particular category by using category id
	 * Way:After getting all product ,check against given category id
	 */
	@GetMapping("/findAllProductsByCategoryId/{categoryId}")
	public List<ProductDTO> findAllProductsByCategoryId(@PathVariable String categoryId) {
		long id = Long.parseLong(categoryId);
		log.debug("<<<<< findAllProductsByCategoryId >>>>>>", id);
		List<ProductDTO> resultantproduct = new ArrayList<ProductDTO>();
		List<ProductDTO> productList = productService.findAll();
		for (ProductDTO product : productList) {
			if (product.getCategoryId() == id) {
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
	public long findStockByCategoryId(@PathVariable String categoryId) {
		long id = Long.parseLong(categoryId);
		List<ProductDTO> productList = productService.findAll();
		long totalStock = 0;
		for (ProductDTO product : productList) {
			if (product.getCategoryId() == id) {				
				totalStock = totalStock + stockService.findOne(product.getStockId()).get().getNoOfItem();
			} 
		}
		return totalStock;
	}
	/**
	 * To get number of stocks by using particular product id
	 *
	 */
	@GetMapping("/findStockByProductId/{productId}")
	public  long findStockByProduct(@PathVariable String productId) {
		log.debug("<<<<< findProduct >>>>>>");
		long id = Long.parseLong(productId);
		return stockService.findOne(productService.findOne(id).get().getStockId()).get().getNoOfItem();
	}
	/**
	 * To get all sales data
	 *
	 */
	@GetMapping("/findAllSales")
	public List<SaleDTO> findAllSales() {
		log.debug("<<<<< findAllSales >>>>>>");
		return saleService.findAll();
	}
}
