package com.process;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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

@SuppressWarnings("unused")
public class NodeType extends Constant {
	private static int NUM = 0;
	private static int flag = 1;

	public static void parseXML(String pathXML, String tagName, String childTag, int levelChild,
			Map<String, String> attrsMap, boolean isHaveAttr, boolean isOnlyAttr)
			throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		KEY_FLAG = false;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xmlDoc = builder.parse(pathXML);
		Element root = (Element) xmlDoc.getDocumentElement();
		findNode(root, tagName, childTag, levelChild, attrsMap, isHaveAttr, isOnlyAttr);
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

	private static boolean checkAttrMulti(Map<String, String> userMap, Map<String, String> mapAt, boolean isOnly) {
		if (isOnly) {
			if (userMap.size() != mapAt.size())
				return false;
		}
		for (Map.Entry<String, String> entry : userMap.entrySet()) {
			if (mapAt.containsKey(entry.getKey().toString())) {
				//mapAt.containsValue(entry.getValue().toString())
				if (mapAt.get(entry.getKey()).equals(entry.getValue())) {
					continue;
				} else
					return false;
			} else
				return false;
		}
		return true;
	}

	private static boolean checkChildExist1(Element element, String childTag) {
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			short type = node.getNodeType();
			switch (type) {
			case Node.ELEMENT_NODE:
				if (node.getNodeName().equals(childTag)) {
					KEY_FLAG = true;
					return true;
				} else {
					checkChildExist1((Element) node, childTag);
					if (KEY_FLAG) {
						return true;
					} else
						continue;
				}
			default:
				break;
			}
		}
		return false;
	}

	private static boolean checkChildExist(Element element, String childTagName, int levelChild, boolean isOnlyChild)
			throws InterruptedException {
		NodeList nodeList = element.getChildNodes();

		stack.push(++LEVEL_COUNT);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			short type = node.getNodeType();
			switch (type) {
			case Node.ELEMENT_NODE:
				if (levelChild != stack.peek()) {
					checkChildExist((Element) node, childTagName, levelChild, isOnlyChild);
					if (LEVEL_COUNT == 0)
						return true;
					break;
				} else {
					if (node.getNodeName().equals(childTagName)) {
						LEVEL_COUNT = 0;
						return true;
					} else {
						continue;
					}
				}
			default:
				break;
			}

		}
		stack.pop();
		LEVEL_COUNT--;
		return false;

	}

	private static int printNode(Element element) throws InterruptedException {
		STR_BUILDER = STR_BUILDER.append("<" + element.getNodeName());
		NamedNodeMap nodeMap = element.getAttributes();
		NodeList nodeList = element.getChildNodes();
		if (nodeMap.getLength() == 0 && nodeList.getLength() == 0) {
			STR_BUILDER = STR_BUILDER.append("/>\n");
			//STR_BUILDER = STR_BUILDER.append("/>");
		} else if (nodeMap.getLength() != 0 && nodeList.getLength() == 0) {
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Attr at = (Attr) nodeMap.item(i);
				STR_BUILDER = STR_BUILDER.append(" " + at.getNodeName() + "=\"" + at.getNodeValue() + "\"");
			}
			STR_BUILDER = STR_BUILDER.append(" />\n");
			//STR_BUILDER = STR_BUILDER.append(" />");
		} else {
			for (int i = 0; i < nodeMap.getLength(); i++) {
				Attr at = (Attr) nodeMap.item(i);
				STR_BUILDER = STR_BUILDER.append(" " + at.getNodeName() + "=\"" + at.getNodeValue() + "\"");
			}
			STR_BUILDER = STR_BUILDER.append(">\n");
			//STR_BUILDER = STR_BUILDER.append(">");
		}

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			short type = node.getNodeType();
			switch (type) {
			case Node.ELEMENT_NODE:
				printNode((Element) node);
				break;
			case Node.ATTRIBUTE_NODE:
				STR_BUILDER = STR_BUILDER.append(node.getNodeValue() + "\n");
				//STR_BUILDER = STR_BUILDER.append(node.getNodeValue());
				break;
			case Node.COMMENT_NODE:
				STR_BUILDER = STR_BUILDER.append("<!-- " + node.getNodeValue() + "-->\n");
				//STR_BUILDER = STR_BUILDER.append("<!-- " + node.getNodeValue() + "-->");
				break;
			case Node.TEXT_NODE:
				break;
			default:
				break;
			}
		}
		if (nodeList.getLength() == 0) {
			return 0;
		} else {
			STR_BUILDER = STR_BUILDER.append("</" + element.getNodeName() + ">\n");
			//STR_BUILDER = STR_BUILDER.append("</" + element.getNodeName()+">");
			return 0;
		}
		
	}

	private static void findNode(Element element, String nodeName, String childTag, int levelChild,
			Map<String, String> attrsMap, boolean isHaveAttr, boolean isOnlyAttr) throws InterruptedException {
		stack.clear();
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			short type = node.getNodeType();
			switch (type) {
			case Node.ELEMENT_NODE:
				if (node.getNodeName().equals(nodeName)) {
					if (!isHaveAttr && childTag.isEmpty()) {
						printNode((Element) node);
						FOUNDED = true;
						continue;
					}
					if (isHaveAttr && childTag.isEmpty()) {
						if (checkAttrMulti(attrsMap, setMap((Element) node), isOnlyAttr)) {
							printNode((Element) node);
							FOUNDED = true;
						} else {
							continue;
						}
					}

					if (!isHaveAttr && !childTag.isEmpty()) {
						KEY_FLAG = false;
						if (checkChildExist1((Element) node, childTag)) {
							printNode((Element) node);
							FOUNDED = true;
							continue;
						}
					}

					if (isHaveAttr && levelChild == 0) {
						KEY_FLAG = false;
						if (checkAttrMulti(attrsMap, setMap((Element) node), isOnlyAttr)) {
							if (checkChildExist1((Element) node, childTag)) {
								printNode((Element) node);
								FOUNDED = true;
							} else

								break;
						} else
							break;
					}
					if (!isHaveAttr && levelChild == 0) {
						KEY_FLAG = false;
						if (checkChildExist1((Element) node, childTag)) {
							printNode((Element) node);
							FOUNDED = true;
						} else
							break;
					}

					if (isHaveAttr && levelChild != 0) {

						if (checkAttrMulti(attrsMap, setMap((Element) node), isOnlyAttr)) {
							if (checkChildExist((Element) node, childTag, levelChild, false)) {
								printNode((Element) node);
								FOUNDED = true;
								break;
							} else {
								continue;
							}
						} else
							break;

					}
					if (!isHaveAttr && levelChild != 0) {
						if (checkChildExist((Element) node, childTag, levelChild, false)) {
							printNode((Element) node);
							FOUNDED = true;
							break;
						} else {
							continue;
						}
					}
				} else {
					findNode((Element) node, nodeName, childTag, levelChild, attrsMap, isHaveAttr, isOnlyAttr);
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

	public static void exportXML(String pathOut,StringBuilder strEXP, boolean isOpen)
			throws IOException, ParserConfigurationException, SAXException, JAXBException {
		FileOutputStream f = new FileOutputStream(new File(pathOut));
		byte[] strTB = strEXP.toString().getBytes();
		f.write(strTB);
		f.close();
		if (isOpen) {
			Desktop desktop = Desktop.getDesktop();
			desktop.open(new File(pathOut));
		}
	}
//	@SuppressWarnings("static-access")
//	public static void main(String[] args) {
//		Map<String, String> testMap = new HashMap<String, String>();
//		NodeType f = new NodeType();
//		try {
//			f.parseXML("D:\\TMAProjects\\Search-Tool\\objects.xml", "Relation", "condition1", 0, testMap, false, false);
//			exportXML();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
