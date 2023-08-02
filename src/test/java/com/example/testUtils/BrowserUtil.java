package com.example.testUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserUtil {

	   public static WebDriver getChromeDriverWithClearedCache() {
		   ChromeOptions options = new ChromeOptions();
	        options.addArguments("--disable-application-cache");
	        options.addArguments("--disk-cache-size=0");
	        options.addArguments("--disable-cache");
	        options.addArguments("--aggressive-cache-discard");
	        options.addArguments("--dns-prefetch-disable");
	        options.addArguments("--disable-offline-load-stale-cache");
	        options.addArguments("--disable-gl-drawing-for-tests");
	        WebDriver driver = new ChromeDriver();
	        return new ChromeDriver(options);
	        }
	      
	    
	
	public static void setupIEDriver() {
		// TODO Auto-generated method stub
		
	}

	public static void setupChromeDriver() {
		// TODO Auto-generated method stub
		
	}
}
