package com.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.constant.Constant;

public class FilterSNMP extends Constant {
	public static void main(String[] args) {
		readFile("D:\\notepad++\\snmpstracing.log", "135.249.41.67");
	}

	private static StringBuilder readFile(String path, String IP) {
		StringBuilder sb = new StringBuilder();
		String line;
		String Object = "";
		int temp = 1;
		try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
			while ((line = br.readLine()) != null) {
				if (temp == 2) {
					try {
						if (checkIP(line, IP)) {
							VALIDIP = true;
						}
					} catch (Exception e) {

					}
				}
				Object += line.toString() + "\n";
				if (line.startsWith("-") && line.endsWith("-")) {
					if (VALIDIP) {
						sb.append(Object);
					}
					VALIDIP = false;
					temp = 0;
					Object = "";
				} else
					temp++;
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		System.out.println(sb.toString());
		return sb;
	}

	private static boolean checkIP(String str, String IPInput) {
		String s[] = str.split(" ");
		s = s[4].split("/");
		if (IPInput.equals(s[0].toString())) {
			return true;
		} else
			return false;
	}
}
