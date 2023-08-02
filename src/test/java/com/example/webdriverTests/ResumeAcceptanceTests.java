package com.example.webdriverTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.example.pageObjects.ResumeBasePageObjects;
import com.example.pageObjects.ResumeTestPageObjects;
import com.example.testUtils.BaseTestNGWebDriver;
import com.example.testUtils.PropertyFileLoader;



@Listeners(com.example.testing.listeners.Listener.class)




public class ResumeAcceptanceTests extends BaseTestNGWebDriver {
	protected static final Logger log = LogManager.getLogger(ResumeAcceptanceTests.class);
	protected PropertyFileLoader testData;

	@BeforeMethod
	public void setUpBrowser() throws Exception {
		
		log.info("Get the browser preference from the TestConfig.properties ");
		driver = getBrowserPreference();
		testData = new PropertyFileLoader();
		String websiteUrl = testData.getProperty("websiteurl");
		String title = testData.getProperty("title");
		if (websiteUrl != null) {
			log.info("Navigate to the Resume webpage");
			ResumeBasePageObjects basePage = new ResumeBasePageObjects(driver, websiteUrl, title);
			
		}
		log.info("Get the System/device/browser information along with date and time of automation run");
		getDeviceDetailsDateAndTimeOfRun();
}

	
	
	/*
	 * This test will navigate to the contact tab, verify if all contact details are
	 * present, and will click on the download resume to download Resume.docx and
	 * will verify if the file got downloaded successfully.
	 */	
	
	@Test(enabled=true)
	public void testDownloadResume() throws Exception {
		
		log.info("This test will navigate to the contact tab, verify if all contact details are present, and will click on the download resume to download Resume.docx and will verify if the file got downloaded successfully.");
		String fileName = testData.getProperty("filename");
		ResumeTestPageObjects testPage = new ResumeTestPageObjects(driver, testData.getProperty("websiteurl"),
				testData.getProperty("title"));
		testPage.downloadResume(fileName);
	}
	

	/*
	 * This test will go to the company name given under work experiences section
	 * and will verify if clicking on the company names redirects to company
	 * websites.Upon successful validation, it again redirects to the main resume
	 * website page.
	 */
	@Test(enabled=true)
	public void testRedirectToCompanyLink() throws Exception {
		
		log.info("This test will go to the company name given under work experiences section and will verify if clicking on the company names redirects to company websites.Upon successful validation, it again redirects to the main resume website page");
		String recentCompany = testData.getProperty("recentcompany");
		String formerCompany = testData.getProperty("formercompany");
		ResumeTestPageObjects objectPage = new ResumeTestPageObjects(driver, testData.getProperty("websiteurl"),
				testData.getProperty("title"));
		objectPage.redirectToRecentCompanyWebsite(recentCompany);
		objectPage.redirectToFormerCompanyWebsite(formerCompany);
	}
	

	
	/*
	 * This test will go to the contacts tab, look for the presence of linkedin url,
	 * which if present will click and test that redirect to linkedIn was successful.
	 * Upon successful validation, it again redirects to the main resume
	 * website page.
	 */
	@Test(enabled=true)
	public void testRedirectToLinkedIn() throws Exception {
		String linkedinurl = testData.getProperty("linkedinUrl");
		ResumeTestPageObjects testPage = new ResumeTestPageObjects(driver, testData.getProperty("websiteurl"),
				testData.getProperty("title"));
		testPage.testLinkedInRedirect(linkedinurl);
	}
	
	
	
	
	/*
	 * This test will go to the skills tab, and verify that a target skill is present in the skill section. 
	 * Target skill to be validated needs to be given in the properties file so it can be read.
	 */
	
	@Test(enabled=true)
	public void testSkillsTab() throws Exception {
		String targetskill = testData.getProperty("targetSkill");
		ResumeTestPageObjects testPage = new ResumeTestPageObjects(driver, testData.getProperty("websiteurl"),
				testData.getProperty("title"));
		testPage.checkSkillPresentOnSkillsTab(targetskill);
	}
		
	
	/*
	 * Verify if contact details and message from the user was submitted
	 * successully.The submitted contact information, is stored in a google sheet
	 * and this test will parse the information stored in the google sheet and
	 * validate if the name, email and message submitted equals the content in the
	 * google sheets
	 */
	
	@Test(enabled=true)
	public void testSubmitMesasge() throws Exception {
		String name = testData.getProperty("name");
		String email = testData.getProperty("email");
		String message = testData.getProperty("message");
		ResumeTestPageObjects objectPage = new ResumeTestPageObjects(driver, testData.getProperty("websiteurl"),
				testData.getProperty("title"));
		objectPage.testSubmitButton(name,email,message);
			
		}

	
	/*
	* 4 Negative scenarios - a)To submit contact name, email, message with invalid
	* data(example. invalid email) and verifying that error checks are in place to
	* prohibit submission of invalid entries. b)Test scenario of submitting contact
	* info which have required values. Verify error checks are in place to prohibit
	* submission of form without contact name or contact email address. c)Test
	* scenario of being able to submit contact form without a message field as
	* message is not a required field. d)Test scenario of being able to submit
	* contact form with long name, email and message body.
	*/

	
	@Test(enabled=true)
	public void testSubmitContactInformation() throws Exception {
		String name = testData.getProperty("testname");
		String email = testData.getProperty("testemail");
		String message = testData.getProperty("testmessage");
		String correctEmail = testData.getProperty("email");
		String maxName = testData.getProperty("maxName");
		String maxEmail = testData.getProperty("maxEmail");
		String maxMessage = testData.getProperty("maxMessage");
		ResumeTestPageObjects objectPage = new ResumeTestPageObjects(driver, testData.getProperty("websiteurl"),
				testData.getProperty("title"));
		objectPage.submitContactInfoWithIncorrectData(name,email,message);
		objectPage.submitContactInfoWithoutRequiredFields(name,email,message);
		objectPage.submitContactInfoWithEmptyMessageField(name,correctEmail,message);//Message field is not required field
		objectPage.testContactInfoWithMaxValues(maxName, maxEmail,maxMessage );
			
		}
	
	
	/*
	* Verify that clicking on 'Scroll to the top button' takes user to the top of
	* the webpage
	*/
	
	@Test(enabled=true)
	public void testScrollToTheTop() throws Exception {
		ResumeTestPageObjects objectPage = new ResumeTestPageObjects(driver, testData.getProperty("websiteurl"),
				testData.getProperty("title"));
		objectPage.testScrollToTheTopButton();
		
	}

	@AfterTest(alwaysRun = true) public void tearDown()
		{ if (driver != null) {
		driver.quit(); } }
	 
}
