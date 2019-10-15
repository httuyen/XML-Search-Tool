package com.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.constant.Constant;

public class FilterSNMP extends Constant {

	public static StringBuilder readFileP(String path, String IP, String nesting, String varBinding, String errorS,
			boolean isKeepN, boolean isKeepVB) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		String Object = "";
		int temp = 1;
		BufferedReader br = Files.newBufferedReader(Paths.get(path));
		try {
			while ((line = br.readLine()) != null) {
				if (temp == 2) {
					VALIDIP = checkIP(line, IP, nesting, isKeepN);
				}
				if(temp > 3) {
					if (!VALID_VARBD) {
						VALID_VARBD = checkVB(line, varBinding, isKeepVB);
					}
					if (!VALID_EST) {
						VALID_EST = checkEST(line, errorS);
					}					
				}
				Object += line.toString() + "\n";
				if (line.isEmpty()) {
					if (VALIDIP && VALID_EST && VALID_VARBD) {
						sb.append(Object);
					}
					VALIDIP = false;
					VALID_EST = false;
					VALID_VARBD = false;
					temp = 1;
					Object = "";
				} else
					temp++;
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		return sb;
	}

	private static boolean checkVB(String str, String varBinding, boolean isKeepVB) {
		if(varBinding == "") return true;
		str = str.replaceAll("\\s+", "-");
		String s[] = {};
		try {
			s = str.split("="); 
			s = s[1].split("-");
			
		}catch (Exception e) {
			return false;
		}
		if (s[1].equals(varBinding)) {
			if (isKeepVB) {
				return true;
			} else
				return false;
		} else
			return false;
	}

	private static boolean checkEST(String str, String errorStatus) {
		if (str.contains(errorStatus) || errorStatus == "") {
			return true;
		} else
			return false;
	}

	private static boolean checkIP(String str, String IPInput, String nesting, boolean isKeepN) {
		if (str.contains(IPInput) || IPInput == "") {
			if (nesting == "")
				return true;
			if (str.contains(nesting)) {
				if (isKeepN) {

					return true;
				} else {
					return false;
				}
			} else
				return true;
		} else
			return false;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(readFileP("D:\\notepad++\\snmptracing.log", "135.249.41.16", "452717", "40", "noError", true, true));
	}
}
