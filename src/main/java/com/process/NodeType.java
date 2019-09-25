package com.process;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.html.dom.NameNodeListImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.constant.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unused")
public class NodeType extends Constant {
	private static int NUM = 0;
	private static int flag = 1;

	public static void parseXML(String pathXML, String tagName, Map<String, String> attrsMap, boolean isNonAttr)
			throws ParserConfigurationException, SAXException, IOException, InterruptedException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xmlDoc = builder.parse(pathXML);
		Element root = (Element) xmlDoc.getDocumentElement();
		// mapAttr.put("from", "ne");
		// mapAttr.put("to","cageMode");
		// System.out.println(isNonAttr);
		findNode(root, tagName, attrsMap, isNonAttr);
	}

	private static void increeTab(int num) {
		String tab = "";
		for (int i = 0; i < num; i++) {
			tab += "\t";
		}
		System.out.print(tab);
	}

	private static NamedNodeMap createListAttr(Element element, NamedNodeMap nodeMap) {

		return nodeMap;
	}

	private static Map<String, String> setMap(Element element) {
		Map<String, String> map = new HashMap<String, String>();
		NamedNodeMap nodeMap = element.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			Attr at = (Attr) nodeMap.item(i);
			map.put(at.getNodeName(), at.getNodeValue());
		}
		return map;
	}

	private static boolean checkAttr(Element element, String strName, String strValue) {
		NamedNodeMap nodeMap = element.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			Attr at = (Attr) nodeMap.item(i);
			if (at.getNodeName().equals(strName)) {
				if (at.getNodeValue().equals(strValue)) {
					return true;
				} else
					continue;
			} else
				continue;
		}
		return false;
	}

	private static boolean checkAttrMulti(Map<String, String> userMap, Map<String, String> mapAt) {
		for (Map.Entry<String, String> entry : userMap.entrySet()) {
			if (mapAt.containsKey(entry.getKey().toString())) {
				if (mapAt.containsValue(entry.getValue().toString())) {
					continue;
				} else
					return false;
			} else
				return false;
		}
		return true;
	}

	private static boolean checkChildExist(Element element, String childTagName) {
		NodeList nodeList = element.getChildNodes();
		if(nodeList.getLength()==0) {
			return false;
		}else {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				short type = node.getNodeType();
				switch (type) {
				case Node.ELEMENT_NODE:
					if(node.getNodeName().equals(childTagName)) {
						return true;
					}
					break;
				default:
					break;
				}
			}
		}
		return false;
	}
	
	private static int printNode(Element element) throws InterruptedException {
		System.out.print("<" + element.getNodeName());
		NamedNodeMap nodeMap = element.getAttributes();
		NodeList nodeList = element.getChildNodes();
		if (nodeMap.getLength() == 0 && nodeList.getLength() == 0) {
			System.out.println("/>");
			// return 1;
		} else if (nodeMap.getLength() != 0 && nodeList.getLength() == 0) {
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Attr at = (Attr) nodeMap.item(i);
				System.out.print(" " + at.getNodeName() + "=\"" + at.getNodeValue() + "\"");
			}
			System.out.println(" />");
			// return 1;
		} else {
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Attr at = (Attr) nodeMap.item(i);
				System.out.print(" " + at.getNodeName() + "=\"" + at.getNodeValue() + "\"");
			}
			System.out.println(">");
		}

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
				break;
			default:
				break;
			}
		}
		// increeTab(num);
		if (nodeList.getLength() == 0) {
			// Thread.sleep(2000);
			return 0;
		} else {
			System.out.println("</" + element.getNodeName() + ">");
			// Thread.sleep(2000);
			return 0;
		}

	}

	private static void findNode(Element element, String nodeName, Map<String, String> attrsMap, boolean isNonAttr)
			throws InterruptedException {
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			short type = node.getNodeType();
			switch (type) {
			case Node.ELEMENT_NODE:
				if (node.getNodeName().equals(nodeName)) {
					if (!isNonAttr) {
						printNode((Element) node);
						break;
					}
					if (checkAttrMulti(attrsMap, setMap((Element) node))) {
						printNode((Element) node);
						break;
					} else
						break;
				} else {
					findNode((Element) node, nodeName, attrsMap, isNonAttr);
					break;
				}
			default:
				break;
			}
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

	private static void print(String s) {
		System.out.print(s);
	}

//	@SuppressWarnings("static-access")
//	public static void main(String[] args) {
//		NodeType f = new NodeType();
//		try {
//			f.parseXML("D:\\EclipseWorkspace\\SearchTool\\java-project\\objects.xml", false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}