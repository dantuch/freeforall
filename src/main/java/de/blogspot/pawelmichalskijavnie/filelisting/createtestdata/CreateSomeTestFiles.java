package de.blogspot.pawelmichalskijavnie.filelisting.createtestdata;

import java.io.File;
import java.io.IOException;

public class CreateSomeTestFiles {
	private static String rootPath;

	public static void main(String[] args) throws IOException {
		rootPath = "C:/test/3";
		File root = new File(rootPath);
		root.mkdir();

		for (String dir : "e,f".split(",")) {
			for (String suffix : "x,y".split(",")) {
				String parent = rootPath + "/" + dir + "/" + suffix;
				new File(parent).mkdirs();
				mkFiles(suffix, parent);
			}
		}

	}

	private static void mkFiles(String a, String parent) throws IOException {
		int count = 5000;
		for (int i = 0; i < count; i++) {
			File file = new File(parent, a + i + ".txt");
			file.createNewFile();
		}
		System.out.println("created new " + count + " files!");

	}

}
