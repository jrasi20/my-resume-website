package com.example.testUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


		public class BaseTestNGWebDriver {
		protected WebDriver driver ;
		public static org.apache.logging.log4j.Logger log = LogManager.getLogger();
		
		@BeforeSuite
	    public void setUp() {
	        if (driver == null) {
	            log.info("Hello World!");
	        }
	    }

	    @AfterSuite
	    public void tearDown() {
	        log.info("Goodbye!");
	        if (driver != null) {
	            driver.quit();
	        }
	    }
		
		public void getDeviceDetailsDateAndTimeOfRun() {
			Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = caps.getBrowserName().toLowerCase();
	        String OS = caps.getPlatformName().toString();
	        String BV = caps.getBrowserVersion().toString();
	        log.info("OS: " + OS + ", Browser: " + browserName + " Browser version " + BV);
	        
	        if (OS.contains("win") || OS.contains("mac") || OS.contains("nix")) {
	            log.info("Device Type: Desktop/Laptop");
	        } else if (OS.contains("android") || OS.contains("ios")) {
	            log.info("Device Type: Mobile Device");
	        } else {
	            log.info("Device Type: Unknown");
	        }
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date currentDate = new Date();
	        String formattedDate = sdf.format(currentDate);
	        log.info("Date and Time of Run: " + formattedDate);
		}
	

			public WebDriver getBrowserPreference() {
		        try {
		            Properties properties = new Properties();
		            String fileName = "TestConfig.properties";
		            InputStream inputStream  = BaseTestNGWebDriver.class.getResourceAsStream("/" + fileName);
		            if (inputStream == null) {
		                throw new RuntimeException("File not found: " + fileName);
		            }
		            properties.load(inputStream);
		            String browserPreference = properties.getProperty("selenium.browser");
		            log.info("Browser preference: " + browserPreference);
		            inputStream.close();
		            
		            if (browserPreference.equalsIgnoreCase("chrome")) {
		                return setupChromeDriver();
		            } else if (browserPreference.equalsIgnoreCase("firefox")) {
		                return setupFirefoxDriver();
		            } else if (browserPreference.equalsIgnoreCase("iexplore")) {
		                return setupIEDriver();
		            } else if (browserPreference.equalsIgnoreCase("safari")) {
		                return setupSafariDriver();
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				return null;
		        
		        
		    }
	
			public WebDriver setupChromeDriver() {
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				String userHome = System.getProperty("user.home");
				String downloadDir = userHome + File.separator + "Downloads";
				ChromeOptions options = new ChromeOptions();
				options.addArguments("download.default_directory=" + downloadDir);
				WebDriver driver = new ChromeDriver();
				return driver;
			}
		
			public WebDriver setupSafariDriver() {
				WebDriver driver = new SafariDriver();
				return driver;
			
			
			}

			public WebDriver setupIEDriver() {
				System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe"); 
				WebDriver driver=new InternetExplorerDriver();
				return driver;
			
					
			}

			public WebDriver setupFirefoxDriver() {
				System.setProperty("webdriver.gecko.driver","./drivers/geckodriver.exe");
			    try {
			    	FirefoxProfile profile = new FirefoxProfile();
			    	FirefoxOptions options = new FirefoxOptions();
			    	options.setProfile(profile);
			        WebDriver driver = new FirefoxDriver(options);
			        log.info("Firefox driver session created successfully.");
			        return driver;
			    } catch (Exception e) {
			        e.printStackTrace();
			        log.info("Error while initializing Firefox driver: " + e.getMessage());
			        return null;
			    }
			}
		
   

}
