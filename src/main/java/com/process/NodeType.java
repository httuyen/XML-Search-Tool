package com.process;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.constant.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NodeType extends Constant {
	private static int NUM = 0;

	@SuppressWarnings("unchecked")
	public static void parseXML(String pathXML)
			throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xmlDoc = builder.parse(pathXML);
		Element root = (Element) xmlDoc.getDocumentElement();

		findNode(root, "MOClass");

		// processNode(root, rootMap);
		// System.out.println(convertObjToMap(rootMap.get("project_17")).get("dependencies_19"));
		// System.out.println(rootMap);

	}

	private static void increeTab(int num) {
		String tab = "";
		for (int i = 0; i < num; i++) {
			tab += "\t";
		}
		System.out.print(tab);
	}

	private static int printNode(Element element) throws InterruptedException {
		System.out.print("<" + element.getNodeName());
		NamedNodeMap nodeMap = element.getAttributes();
		NodeList nodeList = element.getChildNodes();
		if (nodeMap.getLength() == 0 && nodeList.getLength()==0) {
			System.out.println("/>");
			return 1;
		} else if(nodeMap.getLength() != 0 && nodeList.getLength()==0){
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Attr at = (Attr) nodeMap.item(i);
				System.out.print(" " + at.getNodeName() + "=\"" + at.getNodeValue() + "\"");
			}
			System.out.println(" />");
			return 1;
		}else {
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Attr at = (Attr) nodeMap.item(i);
				System.out.print(" " + at.getNodeName() + "=\"" + at.getNodeValue() + "\"");
			}
		}
		System.out.println(">");

		
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			short type = node.getNodeType();
			switch (type) {
			case Node.ELEMENT_NODE:
				printNode((Element) node);
				break;
			case Node.ATTRIBUTE_NODE:
				System.out.println(node.getNodeValue());
				break;
			case Node.COMMENT_NODE:
				System.out.println("<!-- " + node.getNodeValue() + "-->");
				break;
			case Node.TEXT_NODE:
				// System.out.println(node.getNodeValue());
				break;
			default:
				break;
			}
		}
		// increeTab(num);
		System.out.println("</"+element.getNodeName() + ">");
		return 0;
	}

	private static void findNode(Element element, String nodeName) throws InterruptedException {
		int flag = 1;
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			short type = node.getNodeType();
//			if(type == node.ELEMENT_NODE) {
//				System.out.println(node.getNodeName());
//				if(node.getNodeName().equalsIgnoreCase(nodeName)) {
//					printNode((Element) node, node.getNodeValue(), true);// goi method nay cho Element
//				}else {
//					printNode((Element) node, node.getNodeValue(), false);// goi method nay cho Element
//				}				
//			}else 
//			
			switch (type) {
			case Node.ELEMENT_NODE:
				if (node.getNodeName().equalsIgnoreCase(nodeName)) {
					flag = printNode((Element) node);
				} else {
					// printNode((Element) node, node.getNodeValue(), false);// goi method nay cho
					// Element
				}

				break;
//			case Node.COMMENT_NODE:
//				//System.out.println(node.getNodeValue());
//				break;
//			case Node.TEXT_NODE:
//				//print(node.getNodeValue() + "\n");
//				break;
//			case Node.ATTRIBUTE_NODE:
//				print(node.getNodeValue() + "\n");
//				break;
			default:
				break;
			}
			if (flag == 0)
				return;
		}

	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> convertObjToMap(Object obj) {
		Map<String, Object> tempMap = OBJ.convertValue(obj, Map.class);
		return tempMap;

	}

	private static void processNode(Element element, Map<String, Object> rootMap) throws InterruptedException {
		Map<String, Object> childMap = new HashMap<String, Object>();
		NodeList nodeList = element.getChildNodes();
		int count = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			short type = node.getNodeType();
			switch (type) {
			case Node.ELEMENT_NODE:
				processNode((Element) node, childMap);// goi method nay cho Element
				count = i;
				break;
			case Node.COMMENT_NODE:
				break;
			case Node.TEXT_NODE:
				break;
			case Node.ATTRIBUTE_NODE:
				break;
			default:
				break;
			}
		}

		rootMap.put(element.getNodeName() + "_" + count, childMap);
	}

	@SuppressWarnings("unused")
	private static void processNode1(Element element) {
		System.out.println(element.getNodeName());
		NamedNodeMap nodeMap = element.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			// Lay ra nut thu i trong tap hop
			Attr at = (Attr) nodeMap.item(i);
			TEXT = TEXT + " " + at.getNodeName() + "=\"" + at.getNodeValue() + "\"";
			print("\n\t" + at.getNodeName() + "=\"" + at.getNodeValue() + "\"");
		}
		TEXT += ">" + "\n";
		print(">" + "\n");
		// Lay ra danh sach cac nut con cua nut Element element
		NodeList nodeList = element.getChildNodes();
		// System.out.println(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			System.out.println("----------------------" + i + "-------------------------");
			Node node = nodeList.item(i);
			short type = node.getNodeType();
			System.out.println("@@@@@@@@@@@@@@@@@@  " + type + "  @@@@@@@@@@@@@@@@@");
			switch (type) {
			// Truong hop nut con thu i la mot Element
			case Node.ELEMENT_NODE:
				// processNode((Element) node);// goi method nay cho Element
				break;
			case Node.COMMENT_NODE:
				print("<!--" + node.getNodeValue() + "-->\n");

				break;
			case Node.TEXT_NODE:
				print(node.getNodeValue() + "\n");
				break;
			case Node.ATTRIBUTE_NODE:
				print(node.getNodeValue() + "\n");
				break;
			default:
				// print("Other node.....\n");
				break;
			}
		}
	}

	private static void print(String s) {
		System.out.print(s);
	}

	/** Ham main */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		NodeType f = new NodeType();
		try {
			f.parseXML("D:\\EclipseWorkspace\\SearchTool\\java-project\\objects.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
