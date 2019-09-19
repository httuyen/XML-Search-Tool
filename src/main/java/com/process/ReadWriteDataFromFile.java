package com.process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.constant.Constant;

@SuppressWarnings("unused")
public class ReadWriteDataFromFile extends Constant {
//	final static String pathStr = "C:\\apache-maven-3.3.9-bin\\apache-maven-3.3.9\\conf\\settings.xml";
//
//	public static void main(String[] args) throws IOException {
//		ReadWriteDataFromFile obj = new ReadWriteDataFromFile();
//		obj.readData();
//		System.out.println(TEXT);
//	}
	
	public static void readData(String pathStr) throws IOException {
		String text = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(pathStr))) {
			while ((text = br.readLine()) != null) {
				//System.out.println(text);
				TEXT+=text+"\n";
				//checkExist(map, keyword)
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void addData (Map map) {
		Map<String, Map> rootMap = new HashMap<>();
		
	}
	
	private static boolean checkExist (@SuppressWarnings("rawtypes") Map map,String keyword) {
		if(map.containsKey(keyword)) {
			return true;
		}
		return false;
	}
	
	public static Map<String , String> loadData(String pathStr) throws FileNotFoundException, IOException{
		String text="";
		try (BufferedReader br = new BufferedReader(new FileReader(pathStr))) {
			while ((text = br.readLine()) != null) {
				TEXT+=text+"\n";
			}
		}
		return null;
	}
}
