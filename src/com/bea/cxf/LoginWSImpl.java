package com.bea.cxf;


import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;


import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.TimingOutCallback.TimeoutException;

import com.bea.util.PropertiesUtil;
import com.dcfs.teller.security.manager.impl.SecretKeyManager;
import com.isprint.am.xmlrpc.clientcore.ApiProxy;
import com.isprint.am.xmlrpc.clientcore.ApiProxyFactory;

@WebService
public class LoginWSImpl implements LoginWS {
	private static String loginurl = PropertiesUtil.getInstance().prop.getProperty("bea.uim.login.auth");
	private static Logger logger = Logger.getLogger(LoginWSImpl.class);
	
	@Override
	public boolean loginCheck(String username,String password) {
		SecretKeyManager skm = new SecretKeyManager();
		String dskey = skm.decyptSecretKey(password);
		ApiProxy apiProxy = null;
		ApiProxyFactory factory;
		try {
			
			factory = ApiProxyFactory.registerFactory("SimpleFactory",
					new String[] {loginurl}, Integer.parseInt("10"));
			
			apiProxy = factory.createProxy(); 
			
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("realmId", "$IDM");
			
			com.isprint.am.xmlrpc.clientcore.AuthResultTO result = apiProxy
					.getAuthnService().login("", username, dskey, params);
			
			String sessionToken = result.getSession().getSessionToken();
			logger.info("sessionToken参数："+sessionToken);
			logger.info("user:"+username+" 静态密码验证接口：用户验证成功");
			logger.debug("静态密码验证接口：用户验证成功静态密码验证接口：用户验证成功静态密码验证接口：用户验证成功静态密码验证接口：用户验证成功");
			return true;
		} catch (TimeoutException e) {
			logger.error("user:"+username+"验证不通过，",e);
		}
	     catch (NumberFormatException e) {
			logger.error("user:"+username+"验证不通过，报NumberFormatException错误",e);
		}catch (MalformedURLException e) {
			logger.error("user:"+username+"URL参数错误,MalformedURLException");
		} 
		
		catch (XmlRpcException e) {
			if (e.toString().indexOf("UserNotFound") > 0) {
				logger.error("user:"+username+" 静态密码验证接口： 该用户不存在,认证失败");
			} else {
				logger.error("user:"+username+" 静态密码验证接口：用户已经存在,但密码错误,认证失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (apiProxy != null) {
				apiProxy.close();
			}
		}
		return false;
	}
}
