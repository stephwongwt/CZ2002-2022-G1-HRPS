package main.G1HRPS;

import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

/**
 * Provides functions to read and write from database text files.
 * 
 * @author Steph Wong
 *
 */
public class DatabaseHandler {
	/**
	 * Separator to be used when constructing data to be stored.
	 */
	public static final String SEPARATOR = "|";
	
	/**
	 * The path where database files should be stored.
	 */
	private final String DB_PATH = "src\\resources\\";

	/**
	 * Read the contents of the given file.
	 * 
	 * @param fileName the name of file to be read from
	 * @return data read from the file
	 */
	public List read(String fileName) {
		File db_path = new File(DB_PATH);
		File db_file = new File(DB_PATH + fileName);
		if (!db_path.isDirectory()) {
			db_path.mkdirs();
			System.out.println("Path created: " + db_path.getPath());
		}
		List data = new ArrayList();
		Scanner scanner = null;
		if (!db_file.exists()) {
			try {
				if (db_file.createNewFile()) {
					System.out.println("File created: " + db_file.getPath());
				} else {
					System.out.println("File already exists.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			scanner = new Scanner(new FileInputStream(db_file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return data;
	}

	/**
	 * Write fixed content to the given file.
	 * 
	 * @param fileName 		Name of file to be written to
	 * @param data     		Nist of data to be written into the file
	 * @throws IOException	Exception thrown if there's errors reading/writing to file.
	 */
	public void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(DB_PATH + fileName));
		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}
}