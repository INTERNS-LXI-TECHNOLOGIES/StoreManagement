/**
 * 
 */
package com.lxisoft.store.web.rest;

import com.lowagie.text.Header;
import com.lxisoft.store.service.CartService; 
import com.lxisoft.store.service.ProductService;
import com.lxisoft.store.service.QueryService;
import com.lxisoft.store.service.SaleService;

import java.util.ArrayList;
import java.util.List; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lxisoft.store.service.dto.CartDTO;
import com.lxisoft.store.service.dto.ProductDTO;
import com.lxisoft.store.service.dto.SaleDTO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

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
	@Autowired
	private SaleService saleService;
	@Autowired
	private QueryService queryService;
	
	
	/**
	 * GET  /pdf : get the pdf of invoice using database.
	 *  
	 * @return the byte[]
	 * @return the ResponseEntity with status 200 (OK) and the pdf of invoice in body
	 */
	/*@GetMapping("/pdf")
    public ResponseEntity<byte[]> getReportAsPdfUsingDataBase() {
    	
    	log.debug("REST request to get a pdf");
       
        byte[] pdfContents = null;
      
       try
       {
		pdfContents=queryService.getReportAsPdfUsingDataBase();
       }catch(JRException e) {
            e.printStackTrace();
       }
       
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String fileName ="invoice_A4.pdf";
		headers.add("content-disposition", "attachment; filename=" + fileName);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
		            pdfContents, headers, HttpStatus.OK);	        
        return response;
    }*/

	@GetMapping("/pdf/{customerId}")
    public ResponseEntity<byte[]> getReportAsPdfUsingDataBase(@PathVariable Long customerId) {
    	
    	log.debug("REST request to get a pdf ");
       
        byte[] pdfContents = null;
      
        if(customerId !=null) { 
		  
	      try
	      {
	    	  pdfContents=queryService.getReportAsPdfUsingDataBase(customerId);
	    	 
	      }catch(JRException e) {
	            e.printStackTrace();
	      }
      }
             
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String fileName ="invoice.pdf";
		headers.add("content-disposition", "attachment; filename=" + fileName);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
		            pdfContents, headers, HttpStatus.OK);	
		 
  	    return response;
    }

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
		 for (int i=0;i< cartList.size();i++) { 
			if (cartList.get(i).getCustomerId() == customerId) {
				cartListResult.add(cartList.get(i));
				 
			}
		 }
		return cartListResult;
	}
	@GetMapping("/findAllSaleByCustomerId/{customerId}")
	public List<SaleDTO> findAllSaleByCustomerId(@PathVariable Long customerId) {
		log.debug("<<<<< findAllSaleByCustomerId >>>>>>");
		List<SaleDTO> saleList = saleService.findAll();		
		List<SaleDTO> saleListResult=new ArrayList<SaleDTO>(); 
		 for (int i=0;i< saleList.size();i++) { 
			if (saleList.get(i).getCustomerId() == customerId) {
				saleListResult.add(saleList.get(i));
				 
			}
		 }
		return saleListResult;
	}
}



