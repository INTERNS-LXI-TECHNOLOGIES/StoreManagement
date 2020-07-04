/**
 * 
 */
package com.lxisoft.store.web.rest;

import com.lxisoft.store.service.CartService;
import com.lxisoft.store.service.ProductService;

import com.lxisoft.store.service.SaleService;
 
import java.time.Instant;
 
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lxisoft.store.service.dto.CartDTO;
import com.lxisoft.store.service.dto.ProductDTO;
import com.lxisoft.store.service.dto.SaleDTO;
import java.time.Instant;

/**
 *  
 *
 */
@RestController
@RequestMapping("/api/commands")
public class CommandResource {

	private Logger log = LoggerFactory.getLogger(CommandResource.class);
	@Autowired
	private ProductService productService;

	@Autowired
	private SaleService saleService;
	@Autowired
	private CartService cartService;
	/**
	 * Add the list of product to cart
	 *
	 */
	@PostMapping("/addcart/{customerId}/{noOfProduct}")
	public cart addCart(@PathVariable Long customerId,@PathVariable Long noOfProduct, @RequestBody  ProductDTO  productDTO) {
	CartDTO cart =new CartDTO();
	cart.setCustomerId(customerId);
	cart.setNoOfProduct(noOfProduct);
	cart.setAmount((productDTO.getPrice()*noOfProduct));
	cart.setProductId(productDTO.getId());
	cart.setProductName(productDTO.getName());
	cartService.save(cart);
	}
	/**
	 * Add the list of cart to sale
	 *
	 */
 
	@PostMapping("/addsale/{customerId}")
	public void addSale(@PathVariable Long customerId, @RequestBody List<CartDTO> cartDTO) {
		for (CartDTO cart : cartDTO) {
			SaleDTO sale = new SaleDTO();
			Instant instant = Instant.now();
			sale.setAmount(cart.getAmount()); 
			sale.setCustomerId(customerId);
			sale.setDate(instant);
			sale.setNoOfProduct(cart.getNoOfProduct());
			sale.setProductId(cart.getProductId());
			Optional<ProductDTO> product= productService.findOne(cart.getProductId());			 
			product.get().setNoOfStock(product.get().getNoOfStock() - sale.getNoOfProduct());
			productService.save(product.get());	
			sale = saleService.save(sale);
			cartService.delete(cart.getId());
		}
	}

}
