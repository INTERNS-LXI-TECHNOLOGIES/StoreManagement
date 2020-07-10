package com.lxisoft.store.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.lxisoft.store.service.QueryService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;

@Service
@Transactional
public class QueryServiceImpl implements QueryService {
	
	@Autowired
	DataSource dataSource;

	private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);
	
	/**
	    * Gets InvoiceReport : using database.
	    *       	   
	    * @return the byte[].
	    *
	    * @throws JRException.
	    */
	    
	  /*  @Override
	    public byte[] getReportAsPdfUsingDataBase() throws JRException {
	        
	        log.debug("QueryServiceImpl request to get a pdf");
	        
	        JasperReport jr = JasperCompileManager.compileReport("src/main/resources/invoice_A4.jrxml");
	        
	 	      //Preparing parameters
	            Map<String, Object> parameters = new HashMap<String, Object>();
	            
	            Connection conn = null;
	            try {
	                conn = dataSource.getConnection();
	            } catch (SQLException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	            
	 	  JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
	            
	 	  return JasperExportManager.exportReportToPdf(jp);
	            
	            
	    }*/

	@Override
	public byte[] getReportAsPdfUsingDataBase(Long customerId) throws JRException {
		log.debug("QueryServiceImpl request to get a pdf");
        
        JasperReport jr = JasperCompileManager.compileReport("src/main/resources/invoice.jrxml");
        
 	      //Preparing parameters
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("cust_id", customerId);
            
            Connection conn = null;
            try {
                conn = dataSource.getConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
 	  JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
 	 JasperExportManager.exportReportToPdfFile(jp,"src/main/resources/invoice/invoice.pdf");
 	  return JasperExportManager.exportReportToPdf(jp);
	}
	     	
	
}
