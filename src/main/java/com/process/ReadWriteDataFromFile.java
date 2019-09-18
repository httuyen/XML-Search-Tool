package com.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.constant.Constant;

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
			}
		}
	}

}
