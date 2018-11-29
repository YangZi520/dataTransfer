package com.pccw.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import com.pccw.pojo.Root;

public class XmlUtil {
	// 解析指定路径下xml文件
	public static Document parseXmlToDom(String sqlPath) throws SAXException, DocumentException {
		SAXReader reader = new SAXReader();
		reader.setValidation(false);
		reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		return reader.read(new File(sqlPath));
	}

	public static Document parseXmlToDom(File file) throws SAXException, DocumentException {
		SAXReader reader = new SAXReader();
		reader.setValidation(false);
		reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		return reader.read(file);
	}

	public static Document parseXmlToDom(URL url) throws SAXException, DocumentException {
		SAXReader reader = new SAXReader();
		reader.setValidation(false);
		reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		return reader.read(url);
	}

	public static Document parseXmlToDom(InputStream in) throws SAXException, DocumentException{
        SAXReader reader = new SAXReader();
        reader.setValidation(false);
        reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        return reader.read(in);
    }

	/**
	 * 解析配置文件生成java对象
	 * @param path
	 * @return
	 * @throws JAXBException
	 */
	public static Root pareseXmlToJavaBean(InputStream is) throws JAXBException {
		JAXBContext jaxbC = JAXBContext.newInstance(Root.class);
		Unmarshaller us = jaxbC.createUnmarshaller();
		return (Root) us.unmarshal(is);
    }
	
}
