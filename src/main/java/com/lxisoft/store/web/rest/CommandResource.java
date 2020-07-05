/**
 * 
 */
package com.lxisoft.store.web.rest;

import com.lxisoft.store.service.CartService;
import com.lxisoft.store.service.MailService;
import com.lxisoft.store.service.ProductService;

import com.lxisoft.store.service.SaleService;

import java.time.Instant;
import java.util.ArrayList;
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
	@Autowired
	private MailService mailService;

	/**
	 * Add the list of product to cart
	 * 
	 * @author ajay
	 */
	@PostMapping("/addcart/{customerId}")
	public CartDTO addCart(@PathVariable Long customerId, @RequestBody ProductDTO productDTO) {
		CartDTO cartDTO = new CartDTO();
		Long noOfProduct = 1l;
		cartDTO.setCustomerId(customerId);
		cartDTO.setNoOfProduct(noOfProduct);
		cartDTO.setAmount((productDTO.getPrice() * noOfProduct));
		cartDTO.setProductId(productDTO.getId());
		cartDTO.setProductName(productDTO.getName());
		cartDTO = cartService.save(cartDTO);
		return cartDTO;

	}

	/**
	 * Add the list of cart to sale
	 *
	 */

	@PostMapping("/addsale/{customerId}")
	public boolean addSale(@PathVariable Long customerId, @RequestBody List<CartDTO> cartDTO) {
		boolean sucessFlag = true;
		Optional<ProductDTO> product = null;
		List<ProductDTO> productList = new ArrayList<ProductDTO>();
		List<SaleDTO> salesList = new ArrayList<SaleDTO>();
		for (CartDTO cart : cartDTO) {
			SaleDTO sale = new SaleDTO();
			Instant instant = Instant.now();
			sale.setAmount(cart.getAmount());
			sale.setCustomerId(customerId);
			sale.setDate(instant);
			sale.setNoOfProduct(cart.getNoOfProduct());
			sale.setProductId(cart.getProductId());
			salesList.add(sale);
			product = productService.findOne(cart.getProductId());
			if (((product.get().getNoOfStock()) - cart.getNoOfProduct()) < 0) {
				sucessFlag = false;
				break;
			} else
				productList.add(product.get());
		}
		if (sucessFlag) {
			for (int i = 0; i < productList.size(); i++) {
				ProductDTO p = productList.get(i);
				p.setNoOfStock(p.getNoOfStock() - cartDTO.get(i).getNoOfProduct());
				cartService.delete(cartDTO.get(i).getId());
				productService.save(p);
				saleService.save(salesList.get(i));
			}
		}
		return sucessFlag;
	}

}
