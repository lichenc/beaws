package com.bea.cxf;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public interface LoginWS {
		@WebMethod 
		public boolean loginCheck(String name,String password);
		
		
}
