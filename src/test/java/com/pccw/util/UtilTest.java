package com.pccw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.pccw.pojo.Root;

public class UtilTest {
	
	@Test
	public void getDbConfigByName(){
		DbManager dm = new DbManager();
		Map<String, String> retMap = dm.getDBConnectionByName("system");
		System.out.println(retMap.toString());
	}
	
	@Test
	public void parseXml2JavaBean(){
		String path = "config/transfer.xml";
		try {
			Root r = XmlUtil.pareseXmlToJavaBean(new FileInputStream(path));
			System.out.println(r.getTransferInfo().size());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void parseJavaBean2Xml(){
		
	}
}
