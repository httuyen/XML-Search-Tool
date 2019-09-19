package com.process;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.constant.Constant;

public class NodeType extends Constant{
	public static void parseXML(String pathXML) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document xmlDoc = builder.parse(pathXML);
		Element root = (Element) xmlDoc.getDocumentElement();
		processNode(root);

	}

	private static void processNode(Element element) {
		TEXT = TEXT + "<" + element.getNodeName() + "\n";
		print("<" + element.getNodeName());
		// Lay ra danh sach cac thuoc tinh cua Element element
		NamedNodeMap nodeMap = element.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			// Lay ra nut thu i trong tap hop
			Attr at = (Attr) nodeMap.item(i);
			TEXT = TEXT + " "+ at.getNodeName() + "=\"" + at.getNodeValue() + "\"";
			print("\n\t" + at.getNodeName() + "=\"" + at.getNodeValue() + "\"");
		}
		TEXT += ">"+ "\n";
		print(">"  + "\n");
		// Lay ra danh sach cac nut con cua nut Element element
		NodeList nodeList = element.getChildNodes();
		//System.out.println(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			System.out.println("----------------------" + i + "-------------------------");
			Node node = nodeList.item(i);
			short type = node.getNodeType();
			System.out.println("@@@@@@@@@@@@@@@@@@  "+type+"  @@@@@@@@@@@@@@@@@");
			switch (type) {
			// Truong hop nut con thu i la mot Element
			case Node.ELEMENT_NODE:
				processNode((Element) node);// goi method nay cho Element
				break;
			case Node.COMMENT_NODE:
				print("<!--" + node.getNodeValue() + "-->\n");
				
				break;
			case Node.TEXT_NODE:
				print(node.getNodeValue()+"\n");
				break;
			case Node.ATTRIBUTE_NODE:
				print(node.getNodeValue()+"\n");
				break;
			default:
				//print("Other node.....\n");
				break;
			}
		}
	}
	
	private static void processNode1() {
		String xmlFile = "C:\\apache-maven-3.3.9-bin\\apache-maven-3.3.9\\conf\\settings.xml";
		DOMParser parser = new DOMParser();
		try {
		   //Phân tích tài liệu xml .Sau bước này có một cây DOM trong bộ nhớ
		   // chính là hình ảnh của tài liệu
		   parser.parse(xmlFile);
		} catch (SAXException se) {
		   se.printStackTrace();
		} catch (IOException ioe) {
		   ioe.printStackTrace();
		}
		//Lấy ra nút Document mô tả toàn bộ tài liệu XML
		Document document = parser.getDocument();
		System.out.println(document.toString());
	}
	
	private static void print(String s) {
		System.out.print(s);
	}

	/** Ham main */
	public static void main(String[] args) {
		NodeType f = new NodeType();
		try {
			f.parseXML("C:\\apache-maven-3.3.9-bin\\apache-maven-3.3.9\\conf\\settings.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
