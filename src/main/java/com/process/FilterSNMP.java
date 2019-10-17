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
				if (temp > 3) {
					if (!VALID_VARBD) {
						VALID_VARBD = checkVB(line, varBinding, isKeepVB);
					}
					if (!VALID_EST) {
						VALID_EST = checkEST(line, errorS);
					}
				}
				Object += line.toString() + "\n";
				if (line.isEmpty()) {
					if(varBinding != "") {
						if(VALID_VARBD && !isKeepVB) {
							VALID_VARBD = false;
						}
						else if (!VALID_VARBD && !isKeepVB) {
							VALID_VARBD = true;
						}						
					}
					if (VALIDIP && VALID_EST && VALID_VARBD) {
						sb.append(Object);
					}
					VALIDIP = false;
					VALID_EST = false;
					VALID_VARBD = false;
					CHECKED = false;
					temp = 1;
					Object = "";
				} else
					temp++;
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		// System.out.println(sb);
		return sb;
	}

	private static boolean checkVB(String str, String varBinding, boolean isKeepVB) {
		if (varBinding == "")
			return true;
		if (str.contains("= " + varBinding + " binding")) {
			//CHECKED = true;
			return true;
		}else {
			return false;
		}
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

//	public static void main(String[] args) throws IOException {
//		System.out.println(
//				readFileP("D:\\Notepadd++\\logtdemo\\snmptracing.log.1", "135.249.41.68", "", "", "", true, true));
//		// test();
//	}
}
