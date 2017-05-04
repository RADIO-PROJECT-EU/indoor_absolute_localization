package org.atlas.apps.localization.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.atlas.apps.localization.beacons.Measurement;

public class FileUtils {
	
	/**
	 * Create a new file (overrides it if already exists)
	 * @param filename - A fullpath or relative path in order to store the file
	 * @param data - The data to write to file.
	 */
	public static void save(String filename, String data){
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(filename));
			writer.write(data);
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Read a file and return the entire content
	 * @param filename - The filename to read.
	 * @return - The content
	 */
	public static String readAsText(String filename){
		BufferedReader buffer = null;
		String data = "";
		try {
			buffer = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = buffer.readLine()) != null) {
				data += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffer != null) buffer.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return data;
	}
		
	
	
	/**
	 * Read a file of measurements and return a list of that measurements
	 * @param filename - The filename to read.
	 * @param skipTopLines - Some file contain headers on skip them
	 * @return - The content
	 */
	public static List<Measurement> getSampleData(String filename, int skipTopLines){
		BufferedReader buffer = null;
		ArrayList<Measurement> measurements = new ArrayList<Measurement>();
		int header_lines = 0;
		try {
			buffer = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = buffer.readLine()) != null) {
				header_lines++;
				if( header_lines <= skipTopLines ) continue;
				String[] temp = line.split(";");
				measurements.add(new Measurement(temp[0], Integer.parseInt(temp[temp.length-1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffer != null) buffer.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return measurements;
	}
	
}
