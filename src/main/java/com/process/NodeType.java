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

public class NodeType extends Constant {

	public static void parseXML(String pathXML)
			throws ParserConfigurationException, SAXException, IOException, InterruptedException {
		Map<String, Object> rootMap = new HashMap<String, Object>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xmlDoc = builder.parse(pathXML);
		Element root = (Element) xmlDoc.getDocumentElement();
		processNode(root, rootMap);
//		System.out.println(rootMap);
//		System.out.println(rootMap.containsKey("project_17"));
		System.out.println(rootMap.get("project_17"));
		
	}

	private static void processNode(Element element, Map<String, Object> rootMap)
			throws InterruptedException {
		Map<String, Object> childMap = new HashMap<String, Object>();
		NodeList nodeList = element.getChildNodes();
		int count=0;
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
		rootMap.put(element.getNodeName()+"_"+count, childMap);
		System.out.println(rootMap);
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
			f.parseXML("D:\\EclipseWorkspace\\SearchTool\\java-project\\pom.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
