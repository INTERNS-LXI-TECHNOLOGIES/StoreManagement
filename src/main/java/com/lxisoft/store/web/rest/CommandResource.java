/**
 * 
 */
package com.lxisoft.store.web.rest;
 
import com.lxisoft.store.service.CartService; 
import com.lxisoft.store.service.QueryService;
import com.lxisoft.store.service.MailService;
import com.lxisoft.store.service.ProductService;
import com.lxisoft.store.service.UserService;
import com.lxisoft.store.service.SaleService;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 
import com.lxisoft.store.service.dto.CartDTO;
import com.lxisoft.store.service.dto.ProductDTO;
import com.lxisoft.store.service.dto.SaleDTO;

import net.sf.jasperreports.engine.JRException; 
 

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
	@Autowired
	private UserService userService;
	@Autowired
	private QueryService queryService;
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
			sale.setProductName(cart.getProductName());
			product = productService.findOne(cart.getProductId());
			sale.setUnitCost(product.get().getPrice());
			sale.setStoreId(product.get().getStoreId());
			salesList.add(sale);
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
		 /*Sending report to mail*/ 
		try {
			queryService.getReportAsPdfUsingDataBase(customerId);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mailId=userService.getUserWithAuthorities(customerId).get().getEmail();
		String subject="Shopping Bill";
		String content="Sucess";
		
	//	String content=queryService.getReportAsPdfUsingDataBase(customerId);
		mailService.sendEmail(mailId, subject, content,true, true);
		 
		log.debug("<<!!!!!!!!!!"+mailId+"!!!!!!!!!!!!!>>");
		return sucessFlag;
	}

}
