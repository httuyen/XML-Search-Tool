package com.constant;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constant {
	public static String SELECTED = "";
	public static String TEXT = "";
	public static boolean KEY_FLAG = false;
	public static ObjectMapper OBJ = new ObjectMapper();
	public static String xmlString = "";
	public static boolean SELECTION = false;
	protected static Map<String, String> mapAttr = new HashMap<String, String>();

	public static void createMes(Shell shell, String text, String meString) {
		MessageBox messageBox = new MessageBox(shell);
		messageBox.setMessage(meString);
		messageBox.setText(text);
		messageBox.open();
	}

	public static String formatStr(String text) {
		text = text.trim();
		for (int i = 0; i <= text.length(); i++) {
			if(text.charAt(i)=='"') {
				text = text.substring(0, i)+text.substring(i+1, text.length());
			}
		}
		text=text.replaceAll("\\s+", "");
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
	public static void main(String[] args) {
		System.out.println(formatStr("     name=\"asamSystemMIB\"    &&&&&&&&  ;type=\"scalar\""));
	}
}
