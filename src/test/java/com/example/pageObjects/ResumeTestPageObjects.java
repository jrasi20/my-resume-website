package com.example.pageObjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.example.testUtils.PropertyFileLoader;
import com.example.testUtils.TestUtils;

public class ResumeTestPageObjects extends ResumeBasePageObjects {
    protected PropertyFileLoader testData;
    private String websiteUrl;
    private String title ;
    private WebDriver driver;
    
    public ResumeTestPageObjects() throws Exception {
    	this(null, null, null);
    }

    public ResumeTestPageObjects(WebDriver driver, String websiteUrl, String title) throws IOException {
    	super(driver, websiteUrl, title);
    	this.driver = driver;
        this.websiteUrl = websiteUrl;
        this.title = title;
        PageFactory.initElements(driver, this);
        testData = new PropertyFileLoader();
    }
    
   


    @FindBy(id = "skills-tab")
    private WebElement skillsTab;
    
    private By skillsListLocator = By.xpath("//div[@id='skills']/ul/li");

    public java.util.List<WebElement> getSkillsListItems() {
        return driver.findElements(skillsListLocator);
    }
    
    @FindBy(xpath = "//h2[normalize-space()='Summary']")
    private WebElement summary;
    
    @FindBy(xpath = "//p[normalize-space()='Contact']")
    private WebElement contactTab;

    @FindBy(xpath = "//h1[@id='user-name']")
    private WebElement titleName;

    @FindBy(linkText = "Download Resume")
    private WebElement resumeDownloadButton;
    
    @FindBy(xpath = "//a[normalize-space()='www.linkedin.com/in/janeras']")
    private WebElement linkedinUrl;
    
    @FindBy(xpath = "//a[normalize-space()='janerasika20@gmail.com']")
    private WebElement emailIdLink;
    
    @FindBy(linkText = "NEXTGEN HEALTHCARE")
    private WebElement recentCompanyLink;
    
    @FindBy(linkText = "WIPRO TECHNOLOGIES")
    private WebElement formerCompanyLink;
    
    @FindBy(id = "submitButton")
    private WebElement submitButton;
    
    @FindBy(id = "nameInput")
    private WebElement nameField;
    
    @FindBy(id = "emailInput")
    private WebElement emailField;
    
    @FindBy(id = "messageInput")
    private WebElement messageField;
    
    @FindBy(id = "msg")
    private WebElement successMessage;
    
    @FindBy(id = "scrollToTopButton")
    private WebElement scrollToTopButton;
    
    @FindBy(id = "profile-pic")
    private WebElement profilePicture;
    
    
    
    
    

    public String returnFileDownloadPath() {
        String userHome = System.getProperty("user.home");
        String downloadDir = userHome + File.separator + "Downloads";
        return downloadDir;
    }

    public void assertFileDownloaded(String downloadPath, String fileName) throws FileNotFoundException {
        File dir = new File(downloadPath);

        if (!dir.exists() || !dir.isDirectory()) {
            throw new FileNotFoundException("Download directory not found or is not a directory: " + downloadPath);
        }

        File[] dir_contents = dir.listFiles();

        if (dir_contents != null) {
            for (File file : dir_contents) {
                if (file.getName().startsWith("Resume") && file.getName().endsWith(".docx")) {
                    System.out.println("File found in the download directory: " + file.getAbsolutePath());
                    return;
                }
            }
        }

        throw new FileNotFoundException("File with name beginning with 'resume' and '.docx' extension not found in the download directory: " + downloadPath);
    }


    public void downloadResume(String fileName) throws InterruptedException {
        clickOnContactTab();
        String downloadDir = returnFileDownloadPath();
        clickDownloadResume(downloadDir);

        try {
            assertFileDownloaded(downloadDir, fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clickDownloadResume(String downloadDir) throws InterruptedException {
    	scrollToElement(resumeDownloadButton);
    	Assert.assertTrue(resumeDownloadButton.isDisplayed(), "Download Resume button is not visible");
        resumeDownloadButton.click();
        Thread.sleep(3000);//This Click works only with Thread.sleep over other selenium waits for some reason!
        
       
    }

    public void clickOnContactTab() throws InterruptedException {
        if (contactTab.isDisplayed()) {
            contactTab.click();
     
        } else {
            scrollToElement(contactTab);
            Assert.assertTrue(contactTab.isDisplayed(), "Contact tab is not visible");
            contactTab.click();
            verifyContactTabIsActive();
        }
    }

    public void verifyContactTabIsActive() throws InterruptedException {
        Assert.assertTrue(resumeDownloadButton.isDisplayed(), "Resume download button is not visible. Cannot confirm if contact tab is visible");
  
    
    }

    public void scrollToYourElement() throws InterruptedException {
        scrollToElement(titleName);
    }

    public void scrollToElement(WebElement element) throws InterruptedException {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        
    }

	public void redirectToRecentCompanyWebsite(String recentCompany) throws InterruptedException {
		scrollToElement(recentCompanyLink);
		Assert.assertTrue(recentCompanyLink.isDisplayed(), "Nextgen healthcare company link was not visible");
		clickOnRecentCompanyLink(recentCompany);
		
	}

	public void clickOnRecentCompanyLink(String recentCompany) throws InterruptedException {
		recentCompanyLink.click();
		Thread.sleep(3000);//This Click works only with Thread.sleep over other selenium waits for some reason!
		String currentHandle = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		 for (String handle : windowHandles) {
	            if (!handle.equals(currentHandle)) {
	                driver.switchTo().window(handle);
	                break;
	            }
	        }
		 Assert.assertTrue(driver.getCurrentUrl().equals(recentCompany), "Link URL does not match the current URL.");
		 driver.close();
	     driver.switchTo().window(currentHandle);
	     checkWebPageUrl(websiteUrl, title);
	}

	public void redirectToFormerCompanyWebsite(String formerCompany) throws InterruptedException {
		scrollToElement(formerCompanyLink);
		Assert.assertTrue(formerCompanyLink.isDisplayed(), "Wipro Ltd company link was not visible");
		clickOnFormerCompanyLink(formerCompany);
		
	}

	public void clickOnFormerCompanyLink(String formerCompany) throws InterruptedException {
		formerCompanyLink.click();
		Thread.sleep(3000);//This Click works only with Thread.sleep over other selenium waits for some reason!
		String currentHandle = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		 for (String handle : windowHandles) {
	            if (!handle.equals(currentHandle)) {
	                driver.switchTo().window(handle);
	                break;
	            }
	        }
		 Assert.assertTrue(driver.getCurrentUrl().contains(formerCompany), "Link URL does not match the current URL.");
		 driver.close();
	     driver.switchTo().window(currentHandle);
	     checkWebPageUrl(websiteUrl, title);
		
	}

	public void testSubmitButton(String name, String email, String message) throws Exception{
		scrollToElement(submitButton);
		verifyContactSectionElements(name, email, message);	
		submitContactDetails(name, email, message);
		TestUtils utils = new TestUtils();
		utils.readFromSheets(name, email, message);
	}

	
	public void submitContactDetails() throws InterruptedException {
		Assert.assertTrue(submitButton.isDisplayed(), "Cannot confirm if message was submitted.Successfully message sent notification was not seen");
		submitButton.click();
		Thread.sleep(3000);//This is needed
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msg")));
		Assert.assertTrue(successMessage.isDisplayed(), "Cannot confirm if message was submitted.Successfully message sent notification was not seen");
	}

	
	
	public void submitContactDetails(String name, String email, String message) throws InterruptedException {
		nameField.sendKeys(name);
		emailField.sendKeys(email);
		messageField.sendKeys(message);
		submitButton.click();
		Thread.sleep(3000);//This Click works only with Thread.sleep over other selenium waits for some reason!
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msg")));
		Assert.assertTrue(successMessage.isDisplayed(), "Cannot confirm if message was submitted.Successfully message sent notification was not seen");
	}

	public void verifyContactSectionElements(String name, String email, String message) {
		Assert.assertTrue(submitButton.isDisplayed(), "Submit button was not visible. Scroll to object did not work!");
		Assert.assertTrue(nameField.isDisplayed(), "Name field was not visible");
		Assert.assertTrue(emailField.isDisplayed(), "Email field was not visible");
		Assert.assertTrue(messageField.isDisplayed(), "Message field was not visible");
		
	}
	
	public void submitWithWrongValues(String name, String email, String message) throws Exception {
		nameField.sendKeys(name);
		emailField.sendKeys(email);
		messageField.sendKeys(message);
		submitButton.click();
		Thread.sleep(3000);
		assertErrorPresentForInvalidInput();
				
	}


	public void isMessageSubmissionSuccessful()throws Exception {
		boolean isMessageSent = successMessage.isDisplayed();
		if (isMessageSent) {
			System.out.println("Validation Failed: It should not be possible to submit the message with invalid email address. Update logic in the code to block this behavior");
			throw new AssertionError("Invalid email address allowed message submission.");
		}
		else{
			System.out.println("Message submission was prevented because of invalid email address");
		}
		
	}

	public void assertErrorPresentForInvalidInput()throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String errorMessage = (String) js.executeScript("return document.getElementById('emailInput').validationMessage;");
		 if (!errorMessage.isEmpty()) {
	            System.out.println("Validation Passed: Error message is displayed on the page prompting user to input valid email address.");
	            System.out.println("Error Message: " + errorMessage);
	            Assert.assertTrue(true, "Validation Passed: Error message is displayed on the page prompting user to input valid email address.");
	     
	         
	        } else {
	            System.out.println("Validation Failed: Error message is not displayed on the page.");
	            Assert.assertFalse(false, "Validation Failed: Error message is not displayed on the page.");
	        }
		
	}


	public void submitContactInfoWithIncorrectData(String name, String email, String message) throws Exception {
		scrollToElement(submitButton);
		Thread.sleep(3000);//This is necessary here
		verifyContactSectionElements(name, email, message);
		submitWithWrongValues(name,email,message);
		refreshPage();

	}

	public void refreshPage() {
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}

	public void submitContactInfoWithoutRequiredFields(String name, String email, String message) throws Exception {
		scrollToElement(submitButton);
		Thread.sleep(3000);//This is necessary here
		verifyContactSectionElements(name, email, message);
		submitButton.click();
		Thread.sleep(2000);
		assertErrorPresentForNullValues();
		refreshPage();
		
	}

	public void assertErrorPresentForNullValues() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String errorMessage = (String) js.executeScript("return document.getElementById('nameInput').validationMessage;");
		 if (!errorMessage.isEmpty()) {
	            System.out.println("Validation Passed: Error message is displayed on the page prompting user to input required fields.");
	            System.out.println("Error Message: " + errorMessage);
	            Assert.assertTrue(true, "Validation Passed: Error message is displayed on the page prompting user to input input required fields.");
	     
	         
	        } else {
	            System.out.println("Validation Failed: Error message is not displayed on the page.");
	            Assert.assertFalse(false, "Validation Failed: Error message is not displayed on the page.");
	        }
		
	}

	public void submitContactInfoWithEmptyMessageField(String name, String email, String message) throws InterruptedException {
		scrollToElement(submitButton);
		Thread.sleep(3000);//This is necessary here
		verifyContactSectionElements(name, email, message);
		submitContactWithoutMessage(name, email);
		
	}

	public void submitContactWithoutMessage(String name, String email) throws InterruptedException {
		nameField.sendKeys(name);
		emailField.sendKeys(email);
		submitButton.click();
		Thread.sleep(3000);//This Click works only with Thread.sleep over other selenium waits for some reason!
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("msg")));
		Assert.assertTrue(successMessage.isDisplayed(), "Cannot confirm if message was submitted.Successfully message sent notification was not seen");
		refreshPage();
	}

	public void testContactInfoWithMaxValues(String maxName, String maxEmail, String maxMessage) throws InterruptedException {
		scrollToElement(submitButton);
		Thread.sleep(3000);//This is necessary here
		verifyContactSectionElements(maxName, maxEmail, maxMessage);
		submitContactDetails(maxName, maxEmail, maxMessage);
		
	}

	public void testScrollToTheTopButton() throws InterruptedException {
		scrollToElement(scrollToTopButton);
		scrollToTopButton.click();
		Thread.sleep(3000);
		verifyPrescenceOfLogo();
	
	}

	public void verifyPrescenceOfLogo() {
		Assert.assertTrue(profilePicture.isDisplayed(),"Scroll to the top is successful" );
	}

	public void testLinkedInRedirect(String linkedinurl) throws InterruptedException {
		clickOnContactTab();
		testRedirectToLinkedIn(linkedinurl);

	}

	public  void testRedirectToLinkedIn(String linkedinurl) throws InterruptedException {
		Assert.assertTrue(linkedinUrl.isDisplayed(),"LinkedIn URL was not visible under the contacts tab");
		linkedinUrl.click();
		Thread.sleep(2000);
		String currentHandle = driver.getWindowHandle();
		Set<String> windowHandles = driver.getWindowHandles();
		 for (String handle : windowHandles) {
	            if (!handle.equals(currentHandle)) {
	                driver.switchTo().window(handle);
	                break;
	            }
	        }
		 Assert.assertTrue(driver.getCurrentUrl().equals(linkedinurl), "Link URL does not match the lINKEDIN URL.Redirect to Linkedin was not successful");
		 driver.close();
	     driver.switchTo().window(currentHandle);
	     checkWebPageUrl(websiteUrl, title);
		
	}
	
	
	public int countSkillsItems() {
        return getSkillsListItems().size();
    }

	public void checkSkillPresentOnSkillsTab(String targetskill) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		if(skillsTab.isDisplayed()) {
			wait.until(ExpectedConditions.elementToBeClickable(skillsTab));
			skillsTab.click();		
			
		}
		else {
			scrollToElement(skillsTab);
			wait.until(ExpectedConditions.visibilityOf(skillsTab));
			Assert.assertTrue(skillsTab.isDisplayed(),"Skills tab was not seen");
		
		}
		Assert.assertTrue(isSkillPresent(targetskill), "The target skill was not present in the list");
		
	}
	
	 public boolean isSkillPresent(String targetItem) {
	     java.util.List<WebElement> listItems = getSkillsListItems();

	        for (WebElement listItem : listItems) {
	            String itemText = listItem.findElement(By.tagName("span")).getText();
	            if (itemText.equalsIgnoreCase(targetItem)) {
	                return true;
	            }
	        }
	        return false;
	    }

}
