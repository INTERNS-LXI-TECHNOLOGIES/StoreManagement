/**
 * 
 */
package com.lxisoft.store.web.rest;

import com.lxisoft.store.service.ProductService;
import com.lxisoft.store.service.StockService;
import com.lxisoft.store.service.SaleService;
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
import com.lxisoft.store.service.dto.StockDTO;
import com.lxisoft.store.service.dto.SaleDTO;

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
	private StockService stockService;
	@Autowired
	private SaleService saleService;
	/**
	 * To create product
	 * Way:firstly create object for stock using no.of stock to get stockId and by using that 
	 * create object for product
	 *
	 */
	@PostMapping("/addProduct/{noOfStock}")
	public void addProduct(@PathVariable String noOfStock, @RequestBody ProductDTO productDTO) {
		StockDTO s = new StockDTO();
		s.setNoOfItem(Long.parseLong(noOfStock));
		s.setDescription("new stock");
		s = stockService.save(s);
		productDTO.setStockId(s.getId());
		productService.save(productDTO);
	}
	/**
	 *  Add the list of products as sale and by adding to sale simultaneously deduce the stock from stock table
	 *
	 */
	@PostMapping("/addSales")
	public void  addSale(@RequestBody List<SaleDTO> saleDTO) {
		for(SaleDTO s:saleDTO) {
			s=saleService.save(s); 			
			Optional<StockDTO> stockDTO=stockService.findOne(productService.findOne(s.getProductId()).get().getStockId());
			stockDTO.get().setNoOfItem((stockDTO.get().getNoOfItem())-(s.getNoOfProduct()) ); 
			stockService.save(stockDTO.get());
		} 
	}

}
