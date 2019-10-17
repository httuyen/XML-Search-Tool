package com.constant;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.jdom2.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constant {
	// public static Image ICON = new Image(null, ".\\src\\search.ico");
	public static int LEVEL_COUNT = 0;
	public static int CHILD_COUNT = 0;
	public static int TAB_INDEX = -1;
	public static String SELECTED = "";
	public static String URL_OUT = "";
	public static String TEXT = "";
	public static String xmlString = "";
	public static String URL_FILTER = "";
	public static boolean ONLY_ATTR = false;
	public static boolean ONLY_CHILD = false;
	public static boolean HAVE_LEVEL = false;
	public static boolean KEY_FLAG = false;
	public static boolean HAVE_OPEN = false;
	public static boolean HAVE_EXPORT = false;
	public static boolean FOUNDED = false;
	public static boolean VALIDIP = false;
	public static boolean VALID_EST = false;
	public static boolean VALID_VARBD = false;
	public static boolean SEARCH_OPTION = true;
	public static boolean IS_KEEP_N = false;
	public static boolean IS_KEEP_VB = false;
	public static boolean IS_ONLY_VB = false;
	public static boolean LVNULL = true;
	public static boolean CHECKED = false;
	public static String LINE_NUMBER_KEY_NAME = "lineNumber";
	public static ObjectMapper OBJ = new ObjectMapper();
	protected static Map<String, String> mapAttr = new HashMap<String, String>();
	public static Stack<Integer> stack = new Stack<Integer>();
	public static Stack<Integer> stack_tab = new Stack<Integer>();
	public static StringBuilder STR_BUILDER = new StringBuilder();
	public static String [] ITEMS = {"snmptracing.log","mobject.log"};
	
	public static void createMes(Shell shell, String text, String meString) {
		MessageBox messageBox = new MessageBox(shell);
		messageBox.setMessage(meString);
		messageBox.setText(text);
		messageBox.open();
	}

	public static String toPrettyString(String xml, int indent) {
		try {
			// Turn xml string into a document
			Document document = (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));

			// Remove whitespaces outside tags
			((Node) document).normalize();
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']", document,
					XPathConstants.NODESET);

			for (int i = 0; i < nodeList.getLength(); ++i) {
				Node node = nodeList.item(i);
				node.getParentNode().removeChild(node);
			}

			// Setup pretty print options
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", indent);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			// Return pretty print xml string
			StringWriter stringWriter = new StringWriter();
			transformer.transform(new DOMSource((Node) document), new StreamResult(stringWriter));
			return stringWriter.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String formatStr(String text) {
		if (text.isEmpty())
			return text;
		text = text.trim();
		for (int i = 0; i <= text.length(); i++) {
			if (text.charAt(i) == '"') {
				text = text.substring(0, i) + text.substring(i + 1, text.length());
			}
		}
		text = text.replaceAll("\\s+", "");
		return text;
	}

	public static Map<String, String> createMap(String text) {
		Map<String, String> map = new HashMap<String, String>();
		if (text.isEmpty()) {
			map.clear();
			return map;
		} else {
			String[] attrs = text.split(";");
			String[] attr = {};

			for (int i = 0; i < attrs.length; i++) {
				attr = attrs[i].split("=");
				map.put(attr[0], attr[1]);
			}
			return map;
		}
	}

	public static int validLvlInput(String text) {
		if (text.isEmpty())
			return 0;
		text = text.replaceAll("\\s+", "");
		return Integer.valueOf(text);
	}

	public static String createURLOut(String fileName) {
		String str = URL_OUT + "\\" + fileName.replaceAll("\\s+", "") + ".xml";
		return str;
	}

	public static ArrayList<String> listFile(String pathName, String formatFileName) {
		ArrayList<String> ls = new ArrayList<String>();
		File f = new File(pathName);
		File[] files = f.listFiles();
		for (File file : files) {
			if (file.getName().contains("Filtered_"))
				continue;
			if (file.getName().contains(formatFileName)) {
				ls.add(file.getName());
			}
		}
		return ls;
	}

	public static String createPathFilter(String path) {
		return "Filtered_" + path;
	}
//	public static void main(String[] args) {
////		URL_FILTER="D:\\notepad++";
////		System.out.println(listFile(URL_FILTER,"snmpstracing.log" ));
//		
//		String str = "snmpstracing.lg.1";
//		System.out.println(str.contains("snmpstracing.log"));
//	}
}
