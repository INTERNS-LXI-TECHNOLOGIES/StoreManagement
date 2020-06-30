/**
 * 
 */
package com.lxisoft.store.web.rest;

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

	/**
	 * Add the list of products on sale and decrease the stock of product
	 *
	 */
 
	@PostMapping("/addsales/{customerId}")
	public void addSale(@PathVariable Long customerId, @RequestBody List<ProductDTO> productDTO) {
		for (ProductDTO product : productDTO) {
			SaleDTO sale = new SaleDTO();
			Instant instant = Instant.now();
			sale.setAmount(product.getPrice());// amount must mulplied with quatity that to be done later.
			sale.setCustomerId(customerId);
			sale.setDate(instant); 
			sale.setNoOfProduct(product.getNoOfStock());				
			long productId=-1;
			List<ProductDTO> productList = productService.findAll();
			for (ProductDTO p : productList) {
				if (p.getName().equals(product.getName())) {
					productId=p.getId();
				}
			}
			Optional<ProductDTO> pd= productService.findOne(productId);			 
			pd.get().setNoOfStock(pd.get().getNoOfStock() - sale.getNoOfProduct());
			productService.save(pd.get());
			sale.setProductId(productId);	
			sale = saleService.save(sale);	
		}
	}

}
