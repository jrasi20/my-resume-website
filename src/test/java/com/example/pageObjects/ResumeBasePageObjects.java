package com.example.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.example.webdriverTests.ResumeAcceptanceTests;

public class ResumeBasePageObjects extends ResumeAcceptanceTests {

	public ResumeBasePageObjects(WebDriver driver, String websiteurl, String title) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		driver.get(websiteurl);
		maximizeWindow();
		checkWebPageUrl(websiteurl, title);
	}

	
	public void checkWebPageElements(String  websiteurl , String title) {
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, title, "The website title does not macth the expected title");
		
	}
	
	public void checkWebPageUrl(String websiteurl , String title) {
		String actualTitle = driver.getCurrentUrl();
		Assert.assertEquals(actualTitle, websiteurl, "Resume website url does not macth the expected title");
		checkWebPageElements(websiteurl, title);
	}


	public void maximizeWindow() {
		driver.manage().window().maximize();
		
	}

}
