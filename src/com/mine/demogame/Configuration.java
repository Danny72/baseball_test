package com.mine.demogame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {
	
	Properties properties = new Properties();

	public void save_config(String key, int value) {
		
		String path = "res/settings/config.xml";
		
		try {
			File file = new File(path);
			boolean exist = file.exists();
			
			if (!exist) {
				file.createNewFile();
			}
			
			OutputStream output = new FileOutputStream(path);
			properties.setProperty(key, Integer.toString(value));
			properties.storeToXML(output, "resolution");
			
			output.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading file");
			
		}
	}
	
	public void load_config(String path) {
		
		try {
			InputStream input = new FileInputStream(path);
			properties.loadFromXML(input);
			String width = properties.getProperty("width");
			String height = properties.getProperty("height");
			
			Display.width = Integer.parseInt(width);
			Display.height = Integer.parseInt(height);
			
			
			input.close();
			
		} catch (FileNotFoundException e) {
			this.save_config("width", 800);
			this.save_config("height", 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
