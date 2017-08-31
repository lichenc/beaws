package com.bea.cxf;

public class TestClass {
	public TestClass(){
		System.out.println("+++++aaaaaaaa+++++");
	}
	
	public static void main(String[] args) {
		LoginWSImpl ws = new LoginWSImpl();
		if(ws.loginCheck("idmadmin", "password1"))
		{
			System.out.println("登录成功！");
		}else{
			System.out.println("登录失败！");
		}
	}
}
