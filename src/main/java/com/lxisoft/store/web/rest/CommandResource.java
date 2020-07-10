/**
 * 
 */
package com.lxisoft.store.web.rest;
 
import com.lowagie.text.Document;

 

import com.lowagie.text.DocumentException; 

import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table; 
import com.lowagie.text.pdf.PdfWriter;
import com.lxisoft.store.service.CartService; 
import com.lxisoft.store.service.MailService;
import com.lxisoft.store.service.ProductService;
import com.lxisoft.store.service.UserService;
import com.lxisoft.store.service.SaleService;
 
import java.io.FileNotFoundException;
import java.io.FileOutputStream; 
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
	private QueryResource queryResource;

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
			sale.setAmount(cart.getAmount()*cart.getNoOfProduct());
			sale.setCustomerId(customerId);
			sale.setDate(instant); 
			sale.setNoOfProduct(cart.getNoOfProduct());
			sale.setProductId(cart.getProductId());
			sale.setProductName(cart.getProductName());
			sale.setStatus(false);
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
		/* Sending report to mail */
		 
		return sucessFlag;
	}
	 
	@PostMapping("/saveBill/{customerId}")
	public void saveBillByCustomerId(@PathVariable Long customerId) {
		log.debug("inside saveBillByCustomerId");
		Double total = 0.0;
		Document pdfDoc = new Document(PageSize.A4);
		try {
			PdfWriter.getInstance(pdfDoc, new FileOutputStream("src/main/resources/invoice/invoice.pdf"))
					.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
			pdfDoc.open();
			Font myfont = new Font();
			myfont.setStyle(Font.BOLD);
			myfont.setSize(11);
			pdfDoc.add(new Paragraph("\n"));
			List<SaleDTO> saleList = queryResource.findAllSaleByCustomerId(customerId);
			
			Table table= new Table(5);
            table.addCell(" SLNO ");
			table.addCell(" Product Name ");
			table.addCell(" Quatity ");
			table.addCell(" Unit Cost ");
			table.addCell(" Amount ");
			
			for (Integer i = 1; i <= saleList.size(); i++) {
				table.addCell(i.toString());
				table.addCell(saleList.get(i - 1).getProductName());
				table.addCell(saleList.get(i - 1).getAmount().toString());
				table.addCell(saleList.get(i - 1).getNoOfProduct().toString());
				table.addCell(saleList.get(i - 1).getAmount().toString());
				 
				total = total + (saleList.get(i - 1).getAmount());
			}
			table.addCell(" NetTotal ");
			table.addCell(total.toString());
			pdfDoc.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pdfDoc.close();
		String mailId = userService.getUserWithAuthorities(customerId).get().getEmail();
		String subject = "Shopping Bill";
		String content = "Dear customer,Your bill is "+total;
        mailService.sendEmail(mailId, subject, content, true, true);
	}
	@PostMapping("/completePurchase/{customerId}")
	public void completePurchase(@PathVariable Long customerId) {
		List<SaleDTO> saleList = queryResource.findAllSaleByCustomerId(customerId);
		for(SaleDTO s:saleList) {
			s.setStatus(true);
			saleService.save(s);
		}
	}
//	@PostMapping("/myTester")
//	public void Sample()  {  
//	  String string = "hello!"; 
//
//// Get byte array from string 
//byte[] bA=string.getBytes();
//byte[] bA1=null;
//try {
//	  bA1 = queryService.getReportAsPdfUsingDataBase(4L);
//	if(bA1.equals(null))
//		log.debug("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//	else
//		log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//} catch (JRException e4) {
//	// TODO Auto-generated catch block
//	e4.printStackTrace();
//}
//
//String s =new String(bA1, StandardCharsets.UTF_8);
//log.debug("@@@@@@@@@@"+s);
//	 OutputStream os;
//	try {
//		os = new FileOutputStream("src/main/resources/invoice/in.txt");
//		// os.write(bytes);
//		 os.close(); 
//	} catch (FileNotFoundException e3) {
//		// TODO Auto-generated catch block
//		e3.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} 
// 
//	
//	 Document pdfDoc = new Document(PageSize.A4);
//		try {
//			PdfWriter.getInstance(pdfDoc, new FileOutputStream("src/main/resources/invoice/invoice.pdf"))
//			  .setPdfVersion(PdfWriter.PDF_VERSION_1_7);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		pdfDoc.open();
//		Font myfont = new Font();
//		myfont.setStyle(Font.NORMAL);
//		myfont.setSize(11);
//		try {
//			pdfDoc.add(new Paragraph("\n"));
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		BufferedReader br=null;
//		try {
//			br = new BufferedReader(new FileReader("src/main/resources/invoice/in.txt"));
//		} catch (FileNotFoundException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		String strLine;
//		try {
//			while ((strLine = br.readLine()) != null) {
//			    Paragraph para = new Paragraph(strLine + "\n", myfont);
//			    para.setAlignment(Element.ALIGN_JUSTIFIED);
//			    try {
//					pdfDoc.add(para);
//				} catch (DocumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}	
//		pdfDoc.close();
//		try {
//			br.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	 
////	 File file  = new File("src/main/resources/invoice/in.txt");
////	 try {
////		PrintWriter pW=new PrintWriter(file);
////		pW.write("welcome");pW.close();
////	} catch (FileNotFoundException e1) {
////		// TODO Auto-generated catch block
////		e1.printStackTrace();
////	}
////		
////     try {
////		if (file.createNewFile()) {
////		     
////			log.debug("File has been created.");
////		 } else {
////		 
////			 log.debug("File already exists.");
////		 }
////	} catch (IOException e) {
////		// TODO Auto-generated catch block
////		e.printStackTrace();
////	}
//     log.debug("completed");
//	}
//	

}
