package com.Utilities;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Locator {

	/*
	 * public static enum LocatorType{ xpath, linkText, id, name, className,
	 * cssSelector
	 * 
	 * }
	 */
	
	// public Locator(LocatorType locatorType,String location){
	// this.location=location;
	// this.locatorType=locatorType;
	//
	// }

	// public Locator get(LocatorType locatorType,String location){
	//
	// return new Locator(locatorType,location);
	//
	// }

	// To give By
	public static By getBy(String page, String elementName, ExtentTest extlogger) {
		String location;		
		String locatorType;
		boolean Exists = false;
		boolean identifiedbyexists = false;
		By by = null;
		String[] arr1 = new String[2];

		// String IndentifiedBy;
		// String Value;

		// Map locators = new HashMap<>();
		try {
			ReadProperty property = new ReadProperty("config");
			String xmlFileName = property.getProperty("ObjectRepositoryPath");

			File fXmlFile = new File(
					System.getProperty("user.dir") + File.separator + "Resources" + File.separator + xmlFileName);
			if (fXmlFile.exists()) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);

				// Element classElement = doc.getDocumentElement();

				NodeList nList = doc.getElementsByTagName("page");
				int len = nList.getLength();
				boolean PageExists = false;
				for (int i = 0; i < len; i++) {
					Node nd = nList.item(i);
					// NodeList childNodes = nd.getChildNodes();
					String vale = nd.getAttributes().item(0).getTextContent();
					if (vale.equals(page)) {
						PageExists = true;
						NodeList childNodes = nd.getChildNodes();
						int len1 = childNodes.getLength();
						// boolean Exists = false; //moved to global variable
						for (int j = 0; j < len1; j++) {

							// String nodeName =
							// childNodes.item(j).getNodeName();

							/* We are comparing with element tag */
							if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
								if (childNodes.item(j).hasAttributes()) {
									String ne = childNodes.item(j).getAttributes().item(0).getTextContent();

									if (ne.equals(elementName)) {
										Exists = true;
									}

									if (ne.equals(elementName)) {
										NodeList childNodeses = childNodes.item(j).getChildNodes();
										for (int temp = 0; temp < childNodeses.getLength(); temp++) {
											Node nNode = childNodeses.item(temp);

											// boolean identifiedbyexists =
											// false; //moved to global variable

											if (nNode.getNodeType() == Node.ELEMENT_NODE) // To
																							// avoid
																							// #text
																							// nodes
											{
												// System.out.println("\nCurrent
												// Element :" +
												// nNode.getNodeName());
												if (nNode.getNodeName().equals("identifiedby")) {
													// System.out.println("\nCurrent
													// Element :"+ "Value:" +
													// nNode.getTextContent());
													arr1[0] = nNode.getTextContent();
													identifiedbyexists = true;
												}
											}

											if (nNode.getNodeType() == Node.ELEMENT_NODE) {
												if (nNode.getNodeName().equals("value")) {
													// System.out.println("\nCurrent
													// Element :"+ "Value:" +
													// nNode.getTextContent());
													arr1[1] = nNode.getTextContent();
												}
											}
										}
									}
								} else {
									System.out.println("File not found");
									extlogger.log(LogStatus.FAIL, "Locators file not found");
								}
							}

						}

						location = arr1[1];
						locatorType = arr1[0];

						// switch(Locator.locatorType){

						if (locatorType.equals("xpath")) {
							// case locatorType:
							by = By.xpath(location);

						} else if (locatorType.equals("linkText")) {
							by = By.linkText(location);

						} else if (locatorType.equals("id")) {
							// case id:
							by = By.id(location);

						} else if (locatorType.equals("name")) {
							// case name:
							by = By.name(location);

						} else if (locatorType.equals("cssSelector")) {

							// case cssSelector:
							by = By.cssSelector(location);

						} else {

							extlogger.log(LogStatus.FAIL, locatorType + " is not a valid type to identify "
									+ elementName + " in " + page + " page of " + xmlFileName);
						}

					}
				}
				if (!PageExists) {
					System.out.println("Page is not found");
					extlogger.log(LogStatus.FAIL, page + " Page is not found in " + xmlFileName + " file");
				}

			} else {
				System.out.println(xmlFileName + " file not found");
				extlogger.log(LogStatus.FAIL, xmlFileName + " file not found");
			}
		} catch (Exception e) {
			extlogger.log(LogStatus.FAIL, elementName + " is not found in " + page + " of Locators file");
			e.printStackTrace();
			// System.out.println("No Locator Found in XML file");
		}

		return by;
	}

	// To give Location(Xpath or id name or class name)
	public static String getLocation(String page, String elementName, ExtentTest extlogger) {
		
		String location;		
		String locatorType;
		boolean Exists = false;
		boolean identifiedbyexists = false;
		By by = null;
		String[] arr = new String[2];
		
		// String IndentifiedBy;
		// String Value;

		// Map locators = new HashMap<>();
		try {
			ReadProperty property = new ReadProperty("config");
			String xmlFileName = property.getProperty("ObjectRepositoryPath");
			
			
			File fXmlFile = new File(System.getProperty("user.dir")+File.separator+"Resources"+File.separator+xmlFileName);
			if (fXmlFile.exists()) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);

				// Element classElement = doc.getDocumentElement();

				NodeList nList = doc.getElementsByTagName("page");
				int len = nList.getLength();
				boolean PageExists = false;
				for (int i = 0; i < len; i++) {
					Node nd = nList.item(i);
					// NodeList childNodes = nd.getChildNodes();
					String vale = nd.getAttributes().item(0).getTextContent();
					if (vale.equals(page)) {
						PageExists =true;
						NodeList childNodes = nd.getChildNodes();
						int len1 = childNodes.getLength();
//						boolean Exists = false; //moved to global variable
						for (int j = 0; j < len1; j++) {
						
							
							// String nodeName =
							// childNodes.item(j).getNodeName();
							
							/* We are comparing with element tag */
							if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) { 
								if (childNodes.item(j).hasAttributes()) {
									String ne = childNodes.item(j).getAttributes().item(0).getTextContent();
									
									if (ne.equals(elementName))
									{
										Exists = true;
									}
									
									
									if (ne.equals(elementName)) {
										NodeList childNodeses = childNodes.item(j).getChildNodes();
										for (int temp = 0; temp < childNodeses.getLength(); temp++) {
											Node nNode = childNodeses.item(temp);
											
								//			boolean identifiedbyexists = false;   //moved to global variable
									
											if (nNode.getNodeType() == Node.ELEMENT_NODE) // To avoid #text nodes
											{
												// System.out.println("\nCurrent
												// Element :" +
												// nNode.getNodeName());
												if (nNode.getNodeName().equals("identifiedby")) {
													// System.out.println("\nCurrent
													// Element :"+ "Value:" +
													// nNode.getTextContent());
													arr[0] = nNode.getTextContent();
													identifiedbyexists = true;
												}
											}
									
											if (nNode.getNodeType() == Node.ELEMENT_NODE) {
												if (nNode.getNodeName().equals("value")) {
													// System.out.println("\nCurrent
													// Element :"+ "Value:" +
													// nNode.getTextContent());
													arr[1] = nNode.getTextContent();
												}
											}
										}
									}								
								}
								else {
									System.out.println("File not found");
									extlogger.log(LogStatus.FAIL, "Locators file not found");
								}
							}
						
						}
						
						
						location = arr[1];
						locatorType = arr[0];

						// switch(Locator.locatorType){

						if (locatorType.equals("xpath")) {
							// case locatorType:
							by = By.xpath(location);

						} else if (locatorType.equals("linkText")) {
							by = By.linkText(location);

						} else if (locatorType.equals("id")) {
							// case id:
							by = By.id(location);

						} else if (locatorType.equals("name")) {
							// case name:
							by = By.name(location);

						} else if (locatorType.equals("cssSelector")) {

							// case cssSelector:
							by = By.cssSelector(location);

						} else {
							
							extlogger.log(LogStatus.FAIL, locatorType+" is not a valid type to identify "+elementName+" in "+page+" page of "+xmlFileName);
						}


					}
				}
				if(!PageExists)
				{System.out.println("Page is not found");
				extlogger.log(LogStatus.FAIL,page+" Page is not found in "+xmlFileName+" file");
			}
				
			}
			else {
				System.out.println(xmlFileName+" file not found");
				extlogger.log(LogStatus.FAIL, xmlFileName+" file not found");
			}
		} catch (Exception e) {
			extlogger.log(LogStatus.FAIL,elementName+" is not found in "+page+" of Locators file");
			e.printStackTrace();
//			System.out.println("No Locator Found in XML file");
		}

		return arr[1];
	}
	
}
