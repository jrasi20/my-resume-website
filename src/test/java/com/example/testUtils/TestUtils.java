package com.example.testUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsRequestInitializer;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;



public class TestUtils   {
	
	
	 private static final String APPLICATION_NAME = "Google Sheets Verifier";
	 private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
	 private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	 private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	 private static final String TOKENS_DIRECTORY_PATH = "tokens";
	    
	    
	public void readFromSheets(String name, String email, String message)throws Exception {
	
	Properties properties = new Properties();
	try {
        FileInputStream configInput = new FileInputStream("src/main/resources/config.properties");
        properties.load(configInput);
    } catch (IOException e) {
        e.printStackTrace();
        return;
    }
	
	String spreadsheetId = properties.getProperty("spreadsheetId");
    String sheetName = properties.getProperty("sheetName");
    
    verifyLastRow(spreadsheetId, sheetName, name, email, message);
	}

	public void verifyLastRow(String spreadsheetId, String sheetName, String name, String email, String message) {
	    try {
	        Sheets sheetsService = getSheetsService();
	        String range = sheetName + "!A:F";
	        ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, range).execute();
	        List<List<Object>> values = response.getValues();

	        if (values != null && values.size() > 1) {
	            List<Object> lastRow = values.get(values.size() - 1);
	            String actualName = lastRow.get(0).toString();
	            String actualEmail = lastRow.get(1).toString();
	            String actualMessage = lastRow.get(2).toString();

	            Assert.assertEquals(actualName, name, "Verification Failed: Name in the last row does not match the expected value.");
	            Assert.assertEquals(actualEmail, email, "Verification Failed: Email in the last row does not match the expected value.");
	            Assert.assertEquals(actualMessage, message, "Verification Failed: Message in the last row does not match the expected value.");

	            System.out.println("Verification Passed: Last row contains the expected user details.");
	        } else {
	            System.out.println("Verification Failed: No data found in the sheet.");
	        }
	    } catch (IOException | GeneralSecurityException e) {
	        e.printStackTrace();
	    }
	}

	
	public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
		Properties properties = new Properties();
		try {
	        FileInputStream configInput = new FileInputStream("src/main/resources/config.properties");
	        properties.load(configInput);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = authorize(HTTP_TRANSPORT);
        String apiKey = properties.getProperty("apiKey");
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME) .setGoogleClientRequestInitializer(new SheetsRequestInitializer(apiKey))
                .build();
    }
	
	private static Credential authorize(final NetHttpTransport HTTP_TRANSPORT) throws IOException, GeneralSecurityException {
	    try {
	    	
	        // Load the credentials.json file as an InputStream
	        InputStream in = TestUtils.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	        if (in == null) {
	            System.out.println("Failed to read credentials file. InputStream is null.");
	        } else {
	            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

	            // Create the GoogleAuthorizationCodeFlow
	            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
	                    .setAccessType("offline")
	                    .build();

	            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
	            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null; 
	}

	

}
