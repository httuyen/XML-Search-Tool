package com.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.constant.Constant;

public class FilterSNMP extends Constant {

	public static StringBuilder readFileP(String path, String IP, String nesting, boolean isKeep) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		String Object = "";
		int temp = 1;
		BufferedReader br = Files.newBufferedReader(Paths.get(path));
		try {
			while ((line = br.readLine()) != null) {
				if (temp == 2) {
					try {
						if (checkIP(line, IP, nesting, isKeep )) {
							VALIDIP = true;
						}
					} catch (Exception e) {

					}
				}
				Object += line.toString() + "\n";
				
				//if (line.startsWith(" ") && line.endsWith(" ")) {
				if (line.isEmpty()) {
					if (VALIDIP) {
						sb.append(Object);
					}
					VALIDIP = false;
					temp = 1;
					Object = "";
				} else
					temp++;
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		//System.out.println(sb.toString());
		return sb;
	}
	
	private static boolean checkIP(String str, String IPInput, String nesting, boolean isKeep) {
		if(str.contains(IPInput) || IPInput == "") {
			if(nesting == "") return true;
			if(str.contains(nesting)) {
				if(isKeep) {
					
					return true;
				}else {
					return false;
				}
			}else return true;
		}else return false;
		//String s[] = str.split(" ");
		//s = s[4].split("/");
		//if (IPInput.equals(s[0].toString())) {
		//	return true;
		//} else
		//	return false;
		
		
	}
//	public static void main(String[] args) throws IOException {
//		readFileP("D:\\notepad++\\mobject.log", "","452717",false);
//	}
}
