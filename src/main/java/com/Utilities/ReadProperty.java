package com.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadProperty {
	
	private String fileName;
	private File file;
	private Properties prop;
	public ReadProperty(String fileName) {
		
		this.fileName=fileName;
		try {
		String path=System.getProperty("user.dir")+File.separator+"Resources"+File.separator+fileName+".properties";
		file=new File(path);
		}
		catch(Exception e) {
			System.out.println("errors in file reading");
		}
		prop=new Properties();
	}
	
	public String getProperty(String key) {
		String value=null;
		
		try {
			if(file.exists()) {			
			FileInputStream fis=new FileInputStream(file);
			prop.load(fis);
			value=prop.getProperty(key);
			}
			else {
				System.out.println(fileName+" Does not exists");
				
			}		
		}
		catch(Exception e) {
		
		}
		
		
	return value;	
	}
	

	
}
