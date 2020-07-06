package com.lxisoft.store.service;

import net.sf.jasperreports.engine.JRException;

public interface QueryService {
	
	//byte[] getReportAsPdfUsingDataBase() throws JRException;

	byte[] getReportAsPdfUsingDataBase(Long customerId) throws JRException;

}
