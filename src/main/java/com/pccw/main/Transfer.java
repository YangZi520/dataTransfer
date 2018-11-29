package com.pccw.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.pccw.pojo.Root;
import com.pccw.pojo.TransferInfo;
import com.pccw.util.XmlUtil;

public class Transfer
{
	private static final Logger log = Logger.getLogger(Transfer.class);
	
	public static void main(String[] args){
		log.info("开始抽取数据...");
		Transfer insert = new Transfer();
		String path = (args != null && args.length>0) ? args[0] : "config/transfer.xml";
		insert.start(path);
	}
	
	public void start(String path){
		Root root = null;
		try {
			root = XmlUtil.pareseXmlToJavaBean(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			log.error("没有找到配置文件：",e);
		} catch (JAXBException e) {
			e.printStackTrace();
			log.error("解析配置文件出错：",e);
		} 
		if(root == null) return;
		List<TransferInfo> pojos = root.getTransferInfo();
		TransferWithMultiThread t = new TransferWithMultiThread();
		for (int i = 0; i < pojos.size(); i++) {
			t.transfer(pojos.get(i));
		}
		//最后需要执行的sql
		try {
			t.executeCallBack(root.getCallBackInfo());
		} catch (Exception e) {
			log.error("执行回调sql出错：",e);
		}
	}
	
}
