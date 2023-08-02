package com.example.testUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyFileLoader {
	
	private Properties property = new Properties();
	
	public String getPhoneNumber() {
		return property.getProperty("PhoneNumber");
	}
	
	
	public PropertyFileLoader () throws IOException{
        try {
            String fileName = "TEST.properties";
            InputStream inputStream = getClass().getResourceAsStream("/" + fileName);
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + fileName);
            }
            property.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	public PropertyFileLoader(String projectDirectoryName) throws IOException{
		String propertyFilePath = "src/test/resources/TestConfig.properties";
		System.out.println(propertyFilePath);
		Pattern p = Pattern.compile(".+\\\\(.+)\\\\target.+");
		Matcher m = p.matcher(propertyFilePath);
		m.find();
		String currentProjectName = m.group(1);
		propertyFilePath = propertyFilePath.replace(currentProjectName,projectDirectoryName);
		FileReader inputStream = new FileReader(propertyFilePath);
		property.load(inputStream);
		
		
	}


	public String getProperty(String value) {
		return property.getProperty(value);
	}


}
