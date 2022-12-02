package com.qa.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.qa.BaseTest;

public class TestUtils {
	public static final long WAIT=20;

	public HashMap<String, String> parseStringXml(InputStream file) throws Exception{
		HashMap<String, String> stringMap = new HashMap<String, String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document =builder.parse(file);
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList nList = document.getElementsByTagName("string");

		for(int temp=0;temp<nList.getLength();temp++) {
			Node node = nList.item(temp);
			if(node.getNodeType()==Node.ELEMENT_NODE) {
				Element element =(Element) node;
				stringMap.put(element.getAttribute("name"), element.getTextContent());
			}			
		}		
		return stringMap;
	}

	public String dateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void log(String txt) {

		BaseTest base = new BaseTest();
		String msg = Thread.currentThread().getId() + ":" + base.getPlatform() + ":" + base.getDeviceName() + ":" + Thread.currentThread().getStackTrace()[2].getClassName() + ":" + txt;
		String strFile = "Logs" + File.separator + base.getPlatform() + "_" + base.getDeviceName() + File.separator + base.getDateTime();
		File logFile = new File(strFile);

		if(!logFile.exists()) {
			logFile.mkdirs();
		}

		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(logFile + File.separator + "log.txt",true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(msg);
		printWriter.close();
	}

	public Logger log()  {
		return  LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
	}
}